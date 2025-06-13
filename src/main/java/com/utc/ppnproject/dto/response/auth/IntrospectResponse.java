package com.utc.ppnproject.dto.response.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IntrospectResponse {
  
  private boolean valid;
}
