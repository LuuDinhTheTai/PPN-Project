package com.utc.ppnproject.repository;

import com.utc.ppnproject.entity.Product;
import com.utc.ppnproject.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Product, String> {
  
  Optional<Product> findByName(String name);
}
