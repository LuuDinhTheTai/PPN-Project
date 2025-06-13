package com.utc.ppnproject.dto.response.receipt;

import com.utc.ppnproject.entity.Receipt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceiptResponse {
  
  private String id;
  private String customerName;
  private String productName;
  private double price;
  private int qty;
  private Date createdAt;
  private String status;
  private String account;
  
  public static ReceiptResponse from(Receipt receipt) {
    return new ReceiptResponse(
            receipt.getId(),
            receipt.getCustomer().getName(),
            receipt.getProduct().getName(),
            receipt.getProduct().getPrice() * receipt.getQty(),
            receipt.getQty(),
            receipt.getCreatedAt(),
            receipt.getStatus(),
            receipt.getAccount().getUsername()
    );
  }
}
