package com.utc.ppnproject.service.impl;

import com.utc.ppnproject.entity.Role;
import com.utc.ppnproject.repository.RoleRepository;
import com.utc.ppnproject.service.RoleService;
import com.utc.ppnproject.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {
  
  private final RoleRepository repository;
  
  public RoleServiceImpl(RoleRepository repository) {
    super(repository);
    this.repository = repository;
  }
  
  @Override
  public Role findByName(String name) {
    return repository.findByName(name);
  }
}
