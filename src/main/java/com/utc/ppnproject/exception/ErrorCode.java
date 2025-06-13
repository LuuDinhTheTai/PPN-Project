package com.utc.ppnproject.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  
  ACCOUNT_NOT_FOUND(404, "Account not found", HttpStatus.NOT_FOUND),
  ACCOUNT_NOT_EXISTS(1000, "Account not exists", HttpStatus.BAD_REQUEST),
  EMAIL_ALREADY_EXISTS(1001, "Email already exists", HttpStatus.BAD_REQUEST),
  USERNAME_ALREADY_EXISTS(1002, "Username already exists", HttpStatus.BAD_REQUEST),
  USERNAME_INVALID(1003, "Username is invalid", HttpStatus.BAD_REQUEST),
  PASSWORD_INVALID(1004, "Password is invalid", HttpStatus.BAD_REQUEST),
  UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  INVALID_TOKEN(1004, "Invalid token", HttpStatus.UNAUTHORIZED),
  EXPIRED_TOKEN(1005, "Expired token", HttpStatus.UNAUTHORIZED),
  UNSUPPORTED_TOKEN(1006, "Unsupported token", HttpStatus.UNAUTHORIZED),
  PRODUCT_NOT_FOUND(404, "Product not found", HttpStatus.NOT_FOUND),
  CUSTOMER_NOT_FOUND(404, "Customer not found", HttpStatus.NOT_FOUND),
  RECEIPT_NOT_FOUND(404, "Receipt not found", HttpStatus.NOT_FOUND ),;
  
  private final int code;
  private final String message;
  private final HttpStatusCode statusCode;
  
  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}