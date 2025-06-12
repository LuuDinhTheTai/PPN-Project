package com.utc.ppnproject.repository;

import com.utc.ppnproject.entity.Account;
import com.utc.ppnproject.repository.base.BaseRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account, String> {
  
  boolean existsByUsername(@NotEmpty String username);
  Optional<Account> findByUsername(String username);
}
