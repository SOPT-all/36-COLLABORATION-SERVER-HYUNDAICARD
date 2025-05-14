package org.soptcollab.web1.hyundaicard.global.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  // 400번대 (Client Error)
  BLANK_POST_TITLE(HttpStatus.BAD_REQUEST, "c4001", "제목이 비어있습니다."),
  TOO_LONG_POST_TITLE(HttpStatus.BAD_REQUEST, "c4002", "제목은 30글자를 넘을 수 없습니다."),
  BLANK_POST_CONTENT(HttpStatus.BAD_REQUEST, "c4003", "게시글 내용이 비어있습니다."),
  BLANK_USER_NAME(HttpStatus.BAD_REQUEST, "c4004", "유저 이름이 비어있습니다."),
  BLANK_USER_EMAIL(HttpStatus.BAD_REQUEST, "c4005", "유저 이메일이 비어있습니다."),
  TOO_LONG_POST_CONTENT(HttpStatus.BAD_REQUEST, "c4006", "게시글 내용은 1000자를 넘을 수 없습니다."),
  TOO_LONG_USER_NAME(HttpStatus.BAD_REQUEST, "c4007", "닉네임은 10자를 넘을 수 없습니다."),
  ILLEGAL_POST_TAG(HttpStatus.BAD_REQUEST, "c4008", "존재하지 않는 태그입니다."),
  ILLEGAL_POST_SEARCH_SORT(HttpStatus.BAD_REQUEST, "c4009", "존재하지 않는 게시물 검색 종류입니다."),
  DUPLICATE_POST_TITLE(HttpStatus.CONFLICT, "c4090", "이미 존재하는 게시글 제목입니다."),
  USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "c4011", "유저 정보가 존재하지 않습니다."),
  NOT_FOUND_POST(HttpStatus.NOT_FOUND, "c4041", "게시글을 찾을 수 없습니다."),
  TOO_MANY_POST_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "c4290", "게시글은 3분 간격으로만 작성할 수 있습니다."),

  // 500번대 (Server Error)
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "s5000","알 수 없는 서버 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
