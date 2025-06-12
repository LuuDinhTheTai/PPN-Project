package com.utc.ppnproject.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.utc.ppnproject.constant.Constant;
import com.utc.ppnproject.entity.Account;
import com.utc.ppnproject.exception.ApiException;
import com.utc.ppnproject.exception.ErrorCode;
import com.utc.ppnproject.repository.InvalidatedTokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {
  
  @NonFinal
  @Value("${jwt.signerKey}")
  protected String SIGNER_KEY;
  @NonFinal
  @Value("${jwt.valid-duration}")
  protected long VALID_DURATION;
  @NonFinal
  @Value("${jwt.refreshable-duration}")
  protected long REFRESHABLE_DURATION;
  
  private final InvalidatedTokenRepository invalidatedTokenRepository;
  
  public String generateToken(Account account) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
    
    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                        .subject(account.getUsername())
                                        .issuer(Constant.issuer)
                                        .issueTime(new Date())
                                        .expirationTime(new Date(
                                                Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                                        .jwtID(UUID.randomUUID().toString())
                                        .claim("scope", buildScope(account))
                                        .build();
    
    Payload payload = new Payload(jwtClaimsSet.toJSONObject());
    
    JWSObject jwsObject = new JWSObject(header, payload);
    
    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
      
    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }
  
  public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
    JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
    
    SignedJWT signedJWT = SignedJWT.parse(token);
    
    Date expiryTime = (isRefresh)
                              ? new Date(signedJWT
                                                 .getJWTClaimsSet()
                                                 .getIssueTime()
                                                 .toInstant()
                                                 .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                                                 .toEpochMilli())
                              : signedJWT.getJWTClaimsSet().getExpirationTime();
    
    var verified = signedJWT.verify(verifier);
    
    if (!(verified && expiryTime.after(new Date()))) throw new ApiException(ErrorCode.UNAUTHENTICATED);
    
    if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    
    return signedJWT;
  }
  
  
  private String buildScope(Account account) {
    StringJoiner stringJoiner = new StringJoiner(" ");
    
    if (!CollectionUtils.isEmpty(account.getRoles()))
      account.getRoles().forEach(role -> {
        stringJoiner.add("ROLE_" + role.getName());
      });
    
    return stringJoiner.toString();
  }
}
