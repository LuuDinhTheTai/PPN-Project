package com.utc.ppnproject.service;

import com.utc.ppnproject.entity.Role;
import com.utc.ppnproject.service.base.BaseService;

public interface RoleService extends BaseService<Role, String> {
  
  Role findByName(String name);
}
