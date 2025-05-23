package org.soptcollab.web1.hyundaicard.api.service.tag.dto;

import org.soptcollab.web1.hyundaicard.tag.Tag;

public record TagResponseDto(
    String tagId,
    String category,
    String name,
    String hoverText,
    Integer displayOrder
) {

  public static TagResponseDto from(Tag t) {
    return new TagResponseDto(
        t.getId(),
        t.getCategory().getType(),
        t.getName(),
        t.getHoverText(),
        t.getDisplayOrder()
    );
  }

}
