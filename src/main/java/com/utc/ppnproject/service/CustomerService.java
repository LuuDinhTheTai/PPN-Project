package com.utc.ppnproject.service;

import com.utc.ppnproject.dto.request.customer.CreateCustomerRequest;
import com.utc.ppnproject.dto.response.customer.CustomerResponse;
import com.utc.ppnproject.entity.Customer;
import com.utc.ppnproject.service.base.BaseService;

import java.util.Optional;

public interface CustomerService extends BaseService<Customer, String> {
  
  CustomerResponse create(CreateCustomerRequest request);
  Customer findById(String id);
}
