package org.soptcollab.web1.hyundaicard.api.service.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.api.service.post.dto.PostResponseDto;
import org.soptcollab.web1.hyundaicard.domain.post.Post;
import org.soptcollab.web1.hyundaicard.domain.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional(readOnly = true)
  public List<PostResponseDto> list() {
    List<Post> posts = postRepository.findAll();

    return posts.stream().map(PostResponseDto::from).toList();
  }
}
