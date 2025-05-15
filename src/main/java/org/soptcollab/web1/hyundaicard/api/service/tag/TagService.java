package org.soptcollab.web1.hyundaicard.api.service.tag;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.api.service.tag.dto.TagResponseDto;
import org.soptcollab.web1.hyundaicard.tag.TagRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  public List<TagResponseDto> findAll() {

    return tagRepository.findAll().stream()
        .map(tag -> TagResponseDto.from(tag))
        .collect(Collectors.toList());
  }
}
