package org.soptcollab.web1.hyundaicard.api.service.tag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardResponseDto;
import org.soptcollab.web1.hyundaicard.api.service.tag.dto.TagCategoryGroupDto;
import org.soptcollab.web1.hyundaicard.api.service.tag.dto.TagResponseDto;
import org.soptcollab.web1.hyundaicard.tag.TagRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  public List<TagCategoryGroupDto> findAll() {

    List<TagResponseDto> allTags = tagRepository
        .findAll(Sort.by(Sort.Direction.ASC, "displayOrder"))
        .stream()
        .map(TagResponseDto::from)
        .toList();

    // Car
    Map<String, List<TagResponseDto>> groupedByCategory = allTags.stream()
        .collect(Collectors.groupingBy(TagResponseDto::category));

    return groupedByCategory.entrySet().stream()
        .map(entry -> new TagCategoryGroupDto(entry.getKey(), entry.getValue()))
        .toList();
  }
}
