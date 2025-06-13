package com.utc.ppnproject.controller;

import com.utc.ppnproject.dto.ApiResponse;
import com.utc.ppnproject.dto.request.auth.AuthenticationRequest;
import com.utc.ppnproject.dto.request.auth.LogoutRequest;
import com.utc.ppnproject.dto.response.auth.AuthenticationResponse;
import com.utc.ppnproject.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
  
  private final AuthenticationService authenticationService;
  
  @PostMapping("login")
  public ApiResponse<?> login(@Valid @RequestBody AuthenticationRequest request) {
    request.validate();
    return ApiResponse.<AuthenticationResponse>builder()
                   .result(authenticationService.authenticate(request))
                   .build();
  }
  
  @PostMapping("logout")
  public ApiResponse<?> logout(@RequestBody LogoutRequest request) {
    authenticationService.logout(request);
    return ApiResponse.<String>builder()
                   .result("Logged out")
                   .build();
  }
}
