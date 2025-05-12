package org.soptcollab.web1.hyundaicard.exceptions;

import org.soptcollab.web1.hyundaicard.responses.ApiResponse;
import org.soptcollab.web1.hyundaicard.util.LoggingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final LoggingUtil loggingUtil;

  public GlobalExceptionHandler(final LoggingUtil loggingUtil) {
    this.loggingUtil = loggingUtil;
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse<Void>> handleAPIException(ApiException exception) {

    ApiResponse<Void> apiResponse = ApiResponse.error(exception.getErrorCode().getCode(),
        exception.getMessage());

    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception exception) {

    this.loggingUtil.error(exception);

    ApiResponse<Void> apiResponse = ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
        ErrorCode.INTERNAL_SERVER_ERROR.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
  }

}
