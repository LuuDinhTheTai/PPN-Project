package com.utc.ppnproject.dto.request.product;

import com.utc.ppnproject.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductRequest {
  
  private String name;
  private double price;
  private int qty;
  
  public void validate() {
    if (name == null || name.isEmpty()) {
    
    }
  }
  
  public Product toProduct() {
    Product product = new Product();
    product.setName(this.name);
    product.setPrice(this.price);
    product.setQty(this.qty);
    return product;
  }
}
