package org.soptcollab.web1.hyundaicard.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{

  private final ErrorCode code;

  public ApiException(ErrorCode code) {
    this.code = code;
  }

  public HttpStatus getStatus() {
    return code.getStatus();
  }

  @Override
  public String getMessage() {
    return code.getMessage();
  }

  public ErrorCode getErrorCode() {
    return code;
  }
}
