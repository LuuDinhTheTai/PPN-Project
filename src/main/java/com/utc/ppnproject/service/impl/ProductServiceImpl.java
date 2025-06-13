package com.utc.ppnproject.service.impl;

import com.utc.ppnproject.entity.Product;
import com.utc.ppnproject.repository.ProductRepository;
import com.utc.ppnproject.service.ProductService;
import com.utc.ppnproject.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {
  
  public ProductServiceImpl(ProductRepository repository) {
    super(repository);
  }
}
