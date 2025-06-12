package com.utc.ppnproject.exception;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {
  
  private ErrorCode errorCode;
  
  public ApiException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
