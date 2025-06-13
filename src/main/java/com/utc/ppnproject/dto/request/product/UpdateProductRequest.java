package com.utc.ppnproject.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProductRequest {
  
  private String id;
  private String name;
  private double price;
  private int qty;
  
  public void validate() {
  
  }
}
