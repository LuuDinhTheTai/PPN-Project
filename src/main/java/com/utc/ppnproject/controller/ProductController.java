package com.utc.ppnproject.controller;

import com.utc.ppnproject.dto.ApiResponse;
import com.utc.ppnproject.dto.request.product.CreateProductRequest;
import com.utc.ppnproject.dto.request.product.UpdateProductRequest;
import com.utc.ppnproject.dto.response.product.ProductResponse;
import com.utc.ppnproject.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/")
@RequiredArgsConstructor
public class ProductController {
  
  private final ProductService productService;
  
  @PostMapping
  public ApiResponse<?> create(@Valid @RequestBody CreateProductRequest request) {
    request.validate();
    return ApiResponse.<ProductResponse>builder()
                   .result(productService.create(request))
                   .build();
  }
  
  @GetMapping
  public ApiResponse<?> list() {
    return ApiResponse.<List<ProductResponse>>builder()
                   .result(productService.findAll())
                   .build();
  }
  
  @GetMapping("s")
  public ApiResponse<?> findById(@RequestParam String id) {
    return ApiResponse.<ProductResponse>builder()
                   .result(productService.findById(id))
                   .build();
  }
  
  @PutMapping
  public ApiResponse<?> update(@Valid @RequestBody UpdateProductRequest request) {
    request.validate();
    return ApiResponse.<ProductResponse>builder()
                   .result(productService.update(request))
                   .build();
  }
  
  @DeleteMapping
  public ApiResponse<?> delete(@RequestParam String id) {
    productService.delete(id);
    return ApiResponse.<String>builder()
                   .code(HttpStatus.NO_CONTENT.value())
                   .result("Deleted")
                   .build();
  }
}
