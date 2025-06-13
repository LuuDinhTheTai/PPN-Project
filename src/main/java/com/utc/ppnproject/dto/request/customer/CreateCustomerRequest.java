package com.utc.ppnproject.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCustomerRequest {
  
  private String name;
  
  public void validate() {
  
  }
}
