package com.utc.ppnproject.service;

import com.nimbusds.jose.JOSEException;
import com.utc.ppnproject.dto.request.*;
import com.utc.ppnproject.dto.response.AuthenticationResponse;
import com.utc.ppnproject.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
  
  IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
  AuthenticationResponse authenticate(AuthenticationRequest request);
  void logout(LogoutRequest request);
  AuthenticationResponse refreshToken(RefreshRequest request);
}
