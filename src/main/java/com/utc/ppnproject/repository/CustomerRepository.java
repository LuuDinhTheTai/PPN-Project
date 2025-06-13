package com.utc.ppnproject.repository;

import com.utc.ppnproject.entity.Customer;
import com.utc.ppnproject.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, String> {
}
