package com.utc.ppnproject.service;

import com.nimbusds.jose.JOSEException;
import com.utc.ppnproject.dto.request.auth.AuthenticationRequest;
import com.utc.ppnproject.dto.request.auth.IntrospectRequest;
import com.utc.ppnproject.dto.request.auth.LogoutRequest;
import com.utc.ppnproject.dto.request.auth.RefreshRequest;
import com.utc.ppnproject.dto.response.auth.AuthenticationResponse;
import com.utc.ppnproject.dto.response.auth.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
  
  IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
  AuthenticationResponse authenticate(AuthenticationRequest request);
  void logout(LogoutRequest request);
  AuthenticationResponse refreshToken(RefreshRequest request);
}
