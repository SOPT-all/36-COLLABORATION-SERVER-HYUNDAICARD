package org.soptcollab.web1.hyundaicard.api.controller.tag;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "카드 검색 페이지 - 태그 리스트 api", description = "카드 검색 페이지 - 태그 리스트 api")
public class TagController {

  private final TagService tagService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<TagCategoryGroupDto>>> getTags() {

    List<TagCategoryGroupDto> tagList = tagService.findAll();

    return ResponseEntity.ok(ApiResponse.success(tagList));
  }
}
