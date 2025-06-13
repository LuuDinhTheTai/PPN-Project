package com.utc.ppnproject.service;

import com.utc.ppnproject.dto.request.receipt.CreateReceiptRequest;
import com.utc.ppnproject.dto.request.receipt.UpdateReceiptRequest;
import com.utc.ppnproject.dto.response.receipt.ReceiptResponse;
import com.utc.ppnproject.entity.Receipt;
import com.utc.ppnproject.service.base.BaseService;

import java.util.List;

public interface ReceiptService extends BaseService<Receipt, String> {
  
  ReceiptResponse create(CreateReceiptRequest request);
  List<ReceiptResponse> findAll();
  ReceiptResponse findById(String id);
  ReceiptResponse update(UpdateReceiptRequest request);
  ReceiptResponse cancel(String id);
}
