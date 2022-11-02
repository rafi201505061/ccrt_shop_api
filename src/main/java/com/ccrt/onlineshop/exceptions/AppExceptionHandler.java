package com.ccrt.onlineshop.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ccrt.onlineshop.model.response.ResponseMessage;

@ControllerAdvice
public class AppExceptionHandler {

  @ExceptionHandler(value = UserServiceException.class)
  public ResponseEntity<ResponseMessage> handleUserServiceException(UserServiceException userServiceException) {
    return new ResponseEntity<ResponseMessage>(
        new ResponseMessage("USER_SERVICE: " + userServiceException.getCode(), userServiceException.getMessage()),
        userServiceException.getHttpStatus());
  }

  @ExceptionHandler(value = { OtpServiceException.class })
  public ResponseEntity<ResponseMessage> handleOtpServiceException(OtpServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage("OTP_SERVICE: " + exception.getCode(), exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { CategoryServiceException.class })
  public ResponseEntity<ResponseMessage> handleCategoryServiceException(CategoryServiceException exception) {
    ResponseMessage errorMessage = new ResponseMessage("CATEGORY_SERVICE: " + exception.getCode(),
        exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), exception.getHttpStatus());
  }

  @ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
  public ResponseEntity<ResponseMessage> handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException exception) {
    ResponseMessage errorMessage = new ResponseMessage("BAD_REQUEST", exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = { HttpMessageNotReadableException.class })
  public ResponseEntity<ResponseMessage> handleConstraintViolationException(HttpMessageNotReadableException exception) {
    ResponseMessage errorMessage = new ResponseMessage("BAD_REQUEST", "You must provide valid request body.");
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(),
        HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = { Exception.class })
  public ResponseEntity<ResponseMessage> handleUnhandledException(Exception exception) {
    exception.printStackTrace();
    ResponseMessage errorMessage = new ResponseMessage("INTERNAL_SERVER_ERROR", exception.getMessage());
    return new ResponseEntity<ResponseMessage>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
