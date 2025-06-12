package com.utc.ppnproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Receipt {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private Date createdAt;
  private int qty;
  private String status;
  
  @ManyToOne
  @JoinColumn(name = "account_id")
  private Account account;
  
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
}
