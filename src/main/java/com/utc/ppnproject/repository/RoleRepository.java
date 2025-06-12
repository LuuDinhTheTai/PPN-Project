package com.utc.ppnproject.repository;

import com.utc.ppnproject.entity.Role;
import com.utc.ppnproject.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, String> {
  
  Role findByName(String name);
  boolean existsByName(String name);
}
