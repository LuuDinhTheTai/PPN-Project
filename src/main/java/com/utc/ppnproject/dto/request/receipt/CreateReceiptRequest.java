package com.utc.ppnproject.dto.request.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateReceiptRequest {
  
  private String productId;
  private int qty;
  private String customerId;
  
  public void validate() {
  
  }
}
