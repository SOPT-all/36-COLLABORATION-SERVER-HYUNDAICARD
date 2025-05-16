package org.soptcollab.web1.hyundaicard.api.service.post.dto;

import lombok.Builder;
import org.soptcollab.web1.hyundaicard.domain.post.Post;

@Builder
public record PostResponseDto(
    String imageUrl,
    String title
) {

  public static PostResponseDto from(Post post) {
    return PostResponseDto.builder()
        .imageUrl(post.getImage().getUrl())
        .title(post.getTitle())
        .build();
  }
}
