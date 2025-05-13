package org.soptcollab.web1.hyundaicard.global.error.exception;

import org.soptcollab.web1.hyundaicard.global.common.enums.ErrorCode;
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
