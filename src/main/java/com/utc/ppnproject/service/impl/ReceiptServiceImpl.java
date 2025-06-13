package com.utc.ppnproject.service.impl;

import com.utc.ppnproject.constant.Constant;
import com.utc.ppnproject.dto.request.receipt.CreateReceiptRequest;
import com.utc.ppnproject.dto.request.receipt.UpdateReceiptRequest;
import com.utc.ppnproject.dto.response.receipt.ReceiptResponse;
import com.utc.ppnproject.entity.Receipt;
import com.utc.ppnproject.exception.ApiException;
import com.utc.ppnproject.exception.ErrorCode;
import com.utc.ppnproject.repository.ReceiptRepository;
import com.utc.ppnproject.service.AccountService;
import com.utc.ppnproject.service.CustomerService;
import com.utc.ppnproject.service.ProductService;
import com.utc.ppnproject.service.ReceiptService;
import com.utc.ppnproject.service.base.impl.BaseServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReceiptServiceImpl extends BaseServiceImpl<Receipt, String> implements ReceiptService {
  
  private final ReceiptRepository repository;
  private final AccountService accountService;
  private final ProductService productService;
  private final CustomerService customerService;
  
  public ReceiptServiceImpl(ReceiptRepository repository,
                            CustomerService customerService,
                            AccountService accountService,
                            ProductService productService) {
    super(repository);
    this.repository = repository;
    this.customerService = customerService;
    this.accountService = accountService;
    this.productService = productService;
  }
  
  @Override
  public ReceiptResponse create(CreateReceiptRequest request) {
    Receipt receipt = new Receipt();
    
    receipt.setCreatedAt(new Date());
    receipt.setQty(request.getQty());
    receipt.setStatus(Constant.UNPAID);
    
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    receipt.setAccount(accountService.findByUsername(username).orElseThrow());
    
    receipt.setProduct(productService.find(request.getProductId()));
    
    receipt.setCustomer(customerService.findById(request.getCustomerId()));
    
    return ReceiptResponse.from(create(receipt));
  }
  
  @Override
  public List<ReceiptResponse> findAll() {
    return list().stream().map(ReceiptResponse::from).toList();
  }
  
  @Override
  public ReceiptResponse findById(String id) {
    Receipt receipt = find(id);
    if (receipt == null) {
      throw new ApiException(ErrorCode.RECEIPT_NOT_FOUND);
    }
    return ReceiptResponse.from(receipt);
  }
  
  @Override
  public ReceiptResponse update(UpdateReceiptRequest request) {
    Receipt exits = repository.findById(request.getId())
                            .orElseThrow(() -> new ApiException(ErrorCode.RECEIPT_NOT_FOUND));
    
    exits.setStatus(request.getStatus());
    
    return ReceiptResponse.from(update(exits));
  }
  
  @Override
  public ReceiptResponse cancel(String id) {
    Receipt exits = repository.findById(id)
                            .orElseThrow(() -> new ApiException(ErrorCode.RECEIPT_NOT_FOUND));
    
    exits.setStatus(Constant.CANCELLED);
    
    return ReceiptResponse.from(update(exits));
  }
}
