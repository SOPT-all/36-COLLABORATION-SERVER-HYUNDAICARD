package org.soptcollab.web1.hyundaicard.api.controller.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.api.service.post.PostService;
import org.soptcollab.web1.hyundaicard.api.service.post.dto.PostResponseDto;
import org.soptcollab.web1.hyundaicard.global.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

  private final PostService postService;

  @GetMapping()
  public ResponseEntity<ApiResponse<List<PostResponseDto>>> getPostList() {

    return ResponseEntity.ok(ApiResponse.success(postService.list()));
  }
}
