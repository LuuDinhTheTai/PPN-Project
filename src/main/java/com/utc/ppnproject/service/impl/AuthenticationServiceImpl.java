package com.utc.ppnproject.service.impl;

import com.nimbusds.jose.JOSEException;
import com.utc.ppnproject.dto.request.*;
import com.utc.ppnproject.dto.response.AuthenticationResponse;
import com.utc.ppnproject.dto.response.IntrospectResponse;
import com.utc.ppnproject.entity.InvalidatedToken;
import com.utc.ppnproject.exception.ApiException;
import com.utc.ppnproject.exception.ErrorCode;
import com.utc.ppnproject.repository.AccountRepository;
import com.utc.ppnproject.repository.InvalidatedTokenRepository;
import com.utc.ppnproject.security.jwt.JwtUtils;
import com.utc.ppnproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
  
  private final AccountRepository accountRepository;
  private final InvalidatedTokenRepository invalidatedTokenRepository;
  private final JwtUtils jwtUtils;
  
  @Override
  public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
    var token = request.getToken();
    boolean isValid = true;

    try {
      jwtUtils.verifyToken(token, false);
      
    } catch (ApiException e) {
      isValid = false;
    }

    return IntrospectResponse.builder().valid(isValid).build();
  }
  
  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    var account = accountRepository
                       .findByUsername(request.getUsername())
                       .orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_EXISTS));
    
    boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
    
    if (!authenticated) throw new ApiException(ErrorCode.UNAUTHENTICATED);
    
    var token = jwtUtils.generateToken(account);
    
    return AuthenticationResponse.builder().token(token).authenticated(true).build();
  }
  
  @Override
  public void logout(LogoutRequest request) {
    try {
      var signToken = jwtUtils.verifyToken(request.getToken(), true);

      String jit = signToken.getJWTClaimsSet().getJWTID();
      Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

      InvalidatedToken invalidatedToken =
              InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

      invalidatedTokenRepository.save(invalidatedToken);

    } catch (ApiException | JOSEException | ParseException exception) {
      log.info("Token already expired");
    } catch (Exception e) {
      log.error("Error logging out", e);
    }
  }
  
  @Override
  public AuthenticationResponse refreshToken(RefreshRequest request) {
    try {
      var signedJWT = jwtUtils.verifyToken(request.getToken(), true);
      
      var jit = signedJWT.getJWTClaimsSet().getJWTID();
      var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
      
      InvalidatedToken invalidatedToken =
              InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
      
      invalidatedTokenRepository.save(invalidatedToken);
      
      var username = signedJWT.getJWTClaimsSet().getSubject();
      
      var user = accountRepository.findByUsername(username)
                         .orElseThrow(() -> new ApiException(ErrorCode.UNAUTHENTICATED));
      
      var token = jwtUtils.generateToken(user);
      
      return AuthenticationResponse.builder().token(token).authenticated(true).build();
      
    } catch (ApiException | JOSEException | ParseException e) {
      log.info("Token already expired");
    }
    return null;
  }
}
