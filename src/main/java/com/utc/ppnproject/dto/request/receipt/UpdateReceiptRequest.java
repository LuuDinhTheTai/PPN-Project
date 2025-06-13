package com.utc.ppnproject.dto.request.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateReceiptRequest {
  
  private String id;
  private String status;
  
  public void validate() {
  
  }
}
