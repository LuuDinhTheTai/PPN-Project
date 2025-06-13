package com.utc.ppnproject.dto.request.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IntrospectRequest {
  
  private String token;
}
