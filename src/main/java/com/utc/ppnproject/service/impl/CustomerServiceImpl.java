package com.utc.ppnproject.service.impl;

import com.utc.ppnproject.dto.request.customer.CreateCustomerRequest;
import com.utc.ppnproject.dto.response.customer.CustomerResponse;
import com.utc.ppnproject.entity.Customer;
import com.utc.ppnproject.exception.ApiException;
import com.utc.ppnproject.exception.ErrorCode;
import com.utc.ppnproject.repository.CustomerRepository;
import com.utc.ppnproject.service.CustomerService;
import com.utc.ppnproject.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, String> implements CustomerService {
  
  private final CustomerRepository repository;
  
  public CustomerServiceImpl(CustomerRepository repository) {
    super(repository);
    this.repository = repository;
  }
  
  @Override
  public CustomerResponse create(CreateCustomerRequest request) {
    Customer customer = new Customer();
    customer.setName(request.getName());
    return CustomerResponse.from(create(customer));
  }
  
  @Override
  public Customer findById(String id) {
    Customer customer = find(id);
    
    if (customer == null) {
      throw new ApiException(ErrorCode.CUSTOMER_NOT_FOUND);
    }
    
    return customer;
  }
}
