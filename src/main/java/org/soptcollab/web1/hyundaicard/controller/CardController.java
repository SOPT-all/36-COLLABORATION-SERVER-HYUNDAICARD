package org.soptcollab.web1.hyundaicard.controller;

import org.soptcollab.web1.hyundaicard.responses.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

  @GetMapping("/card")
  public HttpEntity<ApiResponse<String>> cardList() {
    return ResponseEntity.ok(ApiResponse.success("테스트"));
  }

}
