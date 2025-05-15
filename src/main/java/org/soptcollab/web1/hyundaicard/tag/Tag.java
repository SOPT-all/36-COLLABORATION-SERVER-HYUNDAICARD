package org.soptcollab.web1.hyundaicard.tag;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soptcollab.web1.hyundaicard.global.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id; // 태그 아이디

  private String name; // 사용자에게 노출되는 태그 명

  private String code; // 내부 식별용 코드

  @Enumerated(EnumType.STRING)
  private Category category;

  private Integer displayOrder;

  private String hoverText; // 태그 호버시 나오는 메시지(ex. 온라인 쇼핑몰 결제시 추가 적립)

  @Builder
  public Tag(final String name, final String code, final Category category,
      final Integer displayOrder, final String hoverText) {
    this.name = name;
    this.code = code;
    this.category = category;
    this.displayOrder = displayOrder;
    this.hoverText = hoverText;
  }


}
