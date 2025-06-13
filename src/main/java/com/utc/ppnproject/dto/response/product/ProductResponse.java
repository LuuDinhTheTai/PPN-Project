package com.utc.ppnproject.dto.response.product;

import com.utc.ppnproject.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
  
  private String id;
  private String name;
  private double price;
  private int qty;
  
  public static ProductResponse from(Product product) {
    return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getQty()
    );
  }
}
