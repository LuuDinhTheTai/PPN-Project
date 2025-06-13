package com.utc.ppnproject.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterRequest {
  
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  @NotEmpty
  private String confirmPassword;
  
  public void validate() {
    if (!this.password.equals(this.confirmPassword)) {
      throw new IllegalArgumentException("Passwords do not match");
    }
  }
}
