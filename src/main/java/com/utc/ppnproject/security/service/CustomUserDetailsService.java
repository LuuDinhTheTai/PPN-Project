package com.utc.ppnproject.security.service;

import com.utc.ppnproject.entity.Account;
import com.utc.ppnproject.exception.ApiException;
import com.utc.ppnproject.exception.ErrorCode;
import com.utc.ppnproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
  
  @Autowired
  private AccountService accountService;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> account = accountService.findByUsername(username);
    
    if (account.isEmpty()) {
      throw new ApiException(ErrorCode.ACCOUNT_NOT_FOUND);
    }
    
    return account.map(acc -> new CustomUserDetails(acc)).orElseThrow(() -> new ApiException(ErrorCode.ACCOUNT_NOT_FOUND));
  }
}
