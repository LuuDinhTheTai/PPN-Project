package com.utc.ppnproject.controller;

import com.utc.ppnproject.dto.ApiResponse;
import com.utc.ppnproject.dto.request.auth.RegisterRequest;
import com.utc.ppnproject.dto.response.auth.AccountResponse;
import com.utc.ppnproject.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/")
public class AccountController {
  
  private final AccountService accountService;
  
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }
  
  @PostMapping("register")
  public ApiResponse<?> register(@Valid @RequestBody RegisterRequest request) {
    request.validate();
    return ApiResponse.<AccountResponse>builder()
                   .code(HttpStatus.CREATED.value())
                   .result(accountService.register(request))
                   .build();
  }
}
