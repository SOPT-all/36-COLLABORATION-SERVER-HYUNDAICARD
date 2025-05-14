package org.soptcollab.web1.hyundaicard.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soptcollab.web1.hyundaicard.global.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @AllArgsConstructor
public class Image extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "IMAGE_ID")
  public String id;

  // 이미지 url
  public String url;

  // 이미지 확장자
  public String extension;

  // 이미지 너비
  public Integer width;

  // 이미지 높이
  public Integer height;





}
