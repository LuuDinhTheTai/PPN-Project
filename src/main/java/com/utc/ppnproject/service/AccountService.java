package com.utc.ppnproject.service;

import com.utc.ppnproject.dto.request.RegisterRequest;
import com.utc.ppnproject.dto.response.AccountResponse;
import com.utc.ppnproject.entity.Account;
import com.utc.ppnproject.service.base.BaseService;

import java.util.Optional;

public interface AccountService extends BaseService<Account, String> {

  AccountResponse register(RegisterRequest request);
  Optional<Account> findByUsername(String username);
  AccountResponse findById(String id);
}
