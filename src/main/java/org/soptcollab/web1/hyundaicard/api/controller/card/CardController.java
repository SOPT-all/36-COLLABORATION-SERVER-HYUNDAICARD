package org.soptcollab.web1.hyundaicard.api.controller.card;

import org.soptcollab.web1.hyundaicard.global.common.response.ApiResponse;
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
