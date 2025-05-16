package org.soptcollab.web1.hyundaicard.api.controller.tag;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.api.service.tag.TagService;
import org.soptcollab.web1.hyundaicard.api.service.tag.dto.TagCategoryGroupDto;
import org.soptcollab.web1.hyundaicard.api.service.tag.dto.TagResponseDto;
import org.soptcollab.web1.hyundaicard.global.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

  private final TagService tagService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<TagCategoryGroupDto>>> getTags() {

    List<TagCategoryGroupDto> tagList = tagService.findAll();

    return ResponseEntity.ok(ApiResponse.success(tagList));
  }
}
