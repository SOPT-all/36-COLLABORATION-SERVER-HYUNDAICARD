package org.soptcollab.web1.hyundaicard.domain.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soptcollab.web1.hyundaicard.Image.Image;
import org.soptcollab.web1.hyundaicard.cardtag.CardTag;
import org.soptcollab.web1.hyundaicard.global.common.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToOne // 1대1 단방향. 일단, image 쪽에는 card 참조를 두지 않았음.
  @JoinColumn(name = "IMAGE_ID") // Card 엔티티의 Image 가 fk 와 매핑
  private Image image;

  @OneToMany(mappedBy = "card")
  @JsonIgnore
  private List<CardTag> cardTags = new ArrayList<>();

  @Column(name = "CARD_NAME")
  private String name;

  @Enumerated(EnumType.STRING)
  private PaymentNetwork paymentNetwork;

  @Enumerated(EnumType.STRING)
  private Brand brand;

  private String benefits;

  private String buttonNote;

  @Builder
  public Card(final Image image, final String name, final PaymentNetwork paymentNetwork,
      final Brand brand, final String benefits,
      final String buttonNote) {
    this.image = image;
    this.name = name;
    this.paymentNetwork = paymentNetwork;
    this.brand = brand;
    this.benefits = benefits;
    this.buttonNote = buttonNote;
  }
}
