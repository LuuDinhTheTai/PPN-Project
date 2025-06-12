package com.utc.ppnproject.service.impl;

import com.utc.ppnproject.constant.Constant;
import com.utc.ppnproject.dto.request.RegisterRequest;
import com.utc.ppnproject.dto.response.AccountResponse;
import com.utc.ppnproject.entity.Account;
import com.utc.ppnproject.entity.Role;
import com.utc.ppnproject.exception.ApiException;
import com.utc.ppnproject.exception.ErrorCode;
import com.utc.ppnproject.repository.AccountRepository;
import com.utc.ppnproject.service.AccountService;
import com.utc.ppnproject.service.RoleService;
import com.utc.ppnproject.service.base.impl.BaseServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, String> implements AccountService {
  
  private final AccountRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  
  public AccountServiceImpl(AccountRepository repository, PasswordEncoder passwordEncoder, RoleService roleService) {
    super(repository);
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
  }
  
  @Override
  public AccountResponse register(RegisterRequest request) {
    Account account = new Account();
    
    if (repository.existsByUsername(request.getUsername())) {
      throw new ApiException(ErrorCode.USERNAME_ALREADY_EXISTS);
    }
    
    account.setUsername(request.getUsername());
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    
    Set<Role> roles = Set.of(roleService.findByName(Constant.ROLE_USER));
    account.setRoles(roles);
    
    return AccountResponse.from(create(account));
  }
  
  @Override
  public Optional<Account> findByUsername(String username) {
    return repository.findByUsername(username);
  }
  
  @Override
  public AccountResponse findById(String id) {
    Optional<Account> account = repository.findById(id);
    if (account.isEmpty()) {
      throw new ApiException(ErrorCode.ACCOUNT_NOT_FOUND);
    }
    return AccountResponse.from(account.get());
  }
}
