package com.utc.ppnproject.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationResponse {
  
  private String token;
  private boolean authenticated;
}
