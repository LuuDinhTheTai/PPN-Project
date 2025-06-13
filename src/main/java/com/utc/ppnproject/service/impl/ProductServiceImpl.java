package com.utc.ppnproject.service.impl;

import com.utc.ppnproject.dto.request.product.CreateProductRequest;
import com.utc.ppnproject.dto.request.product.UpdateProductRequest;
import com.utc.ppnproject.dto.response.product.ProductResponse;
import com.utc.ppnproject.entity.Product;
import com.utc.ppnproject.exception.ApiException;
import com.utc.ppnproject.exception.ErrorCode;
import com.utc.ppnproject.repository.ProductRepository;
import com.utc.ppnproject.service.ProductService;
import com.utc.ppnproject.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {
  
  private final ProductRepository repository;
  
  public ProductServiceImpl(ProductRepository repository) {
    super(repository);
    this.repository = repository;
  }
  
  @Override
  public ProductResponse create(CreateProductRequest request) {
    return ProductResponse.from(create(request.toProduct()));
  }
  
  @Override
  public List<ProductResponse> findAll() {
    return list().stream().map(ProductResponse::from).toList();
  }
  
  @Override
  public ProductResponse findById(String id) {
    return ProductResponse.from(find(id));
  }
  
  @Override
  public ProductResponse update(UpdateProductRequest request) {
    Product exits = repository.findById(request.getId())
                            .orElseThrow(() -> new ApiException(ErrorCode.PRODUCT_NOT_FOUND));
    
    exits.setName(request.getName());
    exits.setPrice(request.getPrice());
    exits.setQty(request.getQty());
    
    return ProductResponse.from(update(exits));
  }
  
  
}
