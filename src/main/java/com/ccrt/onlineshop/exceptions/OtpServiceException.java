package com.ccrt.onlineshop.exceptions;

import org.springframework.http.HttpStatus;

public class OtpServiceException extends RuntimeException {
  private String code;
  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public OtpServiceException(String message) {
    super(message);
  }

  public OtpServiceException(String code, String message) {
    super(message);
    this.code = code;
  }

  public OtpServiceException(String code, String message, HttpStatus httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}
