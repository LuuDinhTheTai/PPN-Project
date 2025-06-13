package com.utc.ppnproject.controller;

import com.utc.ppnproject.dto.ApiResponse;
import com.utc.ppnproject.dto.request.receipt.CreateReceiptRequest;
import com.utc.ppnproject.dto.request.receipt.UpdateReceiptRequest;
import com.utc.ppnproject.dto.response.receipt.ReceiptResponse;
import com.utc.ppnproject.service.ReceiptService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipts/")
@RequiredArgsConstructor
public class ReceiptController {
  
  private final ReceiptService receiptService;
  
  @PostMapping
  public ApiResponse<?> create(@Valid @RequestBody CreateReceiptRequest request) {
    request.validate();
    return ApiResponse.<ReceiptResponse>builder()
                   .result(receiptService.create(request))
                   .build();
  }
  
  @GetMapping
  public ApiResponse<?> list() {
    return ApiResponse.<List<ReceiptResponse>>builder()
                   .result(receiptService.findAll())
                   .build();
  }
  
  @GetMapping("s")
  public ApiResponse<?> findById(@RequestParam String id) {
    return ApiResponse.<ReceiptResponse>builder()
                   .result(receiptService.findById(id))
                   .build();
  }
  
  @PutMapping
  public ApiResponse<?> update(@Valid @RequestBody UpdateReceiptRequest request) {
    request.validate();
    return ApiResponse.<ReceiptResponse>builder()
                   .result(receiptService.update(request))
                   .build();
  }
  
  @PostMapping("cancel")
  public ApiResponse<?> delete(@RequestParam String id) {
    return ApiResponse.<ReceiptResponse>builder()
                   .result(receiptService.cancel(id))
                   .build();
  }
}
