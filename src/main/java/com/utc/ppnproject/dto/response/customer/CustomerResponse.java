package com.utc.ppnproject.dto.response.customer;

import com.utc.ppnproject.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerResponse {
  
  private String id;
  private String name;
  
  public static CustomerResponse from(Customer customer) {
    return new CustomerResponse(
            customer.getId(),
            customer.getName()
    );
  }
}
