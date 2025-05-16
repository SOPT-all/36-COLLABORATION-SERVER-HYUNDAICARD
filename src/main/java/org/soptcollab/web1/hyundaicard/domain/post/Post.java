package org.soptcollab.web1.hyundaicard.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soptcollab.web1.hyundaicard.Image.Image;
import org.soptcollab.web1.hyundaicard.global.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToOne
  @JoinColumn(name = "image_id")
  private Image image;

  private String title;

  @Builder
  public Post(final Image image, final String title) {
    this.image = image;
    this.title = title;
  }
}
