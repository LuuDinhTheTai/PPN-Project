package com.utc.ppnproject.service;

import com.utc.ppnproject.dto.request.product.CreateProductRequest;
import com.utc.ppnproject.dto.request.product.UpdateProductRequest;
import com.utc.ppnproject.dto.response.product.ProductResponse;
import com.utc.ppnproject.entity.Product;
import com.utc.ppnproject.service.base.BaseService;

import java.util.List;

public interface ProductService extends BaseService<Product, String> {
  
  ProductResponse create(CreateProductRequest request);
  List<ProductResponse> findAll();
  ProductResponse findById(String id);
  ProductResponse update(UpdateProductRequest request);
}
