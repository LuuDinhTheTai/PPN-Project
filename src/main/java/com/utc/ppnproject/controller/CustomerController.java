package com.utc.ppnproject.controller;

import com.utc.ppnproject.dto.ApiResponse;
import com.utc.ppnproject.dto.request.customer.CreateCustomerRequest;
import com.utc.ppnproject.dto.response.customer.CustomerResponse;
import com.utc.ppnproject.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers/")
@RequiredArgsConstructor
public class CustomerController {
  
  private final CustomerService customerService;
  
  @PostMapping
  public ApiResponse<?> create(@Valid @RequestBody CreateCustomerRequest request) {
    request.validate();
    return ApiResponse.<CustomerResponse>builder()
                   .result(customerService.create(request))
                   .build();
  }
}
