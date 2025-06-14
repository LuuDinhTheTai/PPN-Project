package com.utc.ppnproject.dto.response.auth;

import com.utc.ppnproject.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountResponse {
  
  private String id;
  private String username;
  private String password;
  
  public static AccountResponse from(Account account) {
    return new AccountResponse(
            account.getId(),
            account.getUsername(),
            account.getPassword()
    );
  }
}
