package org.soptcollab.web1.hyundaicard.cardtag;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.soptcollab.web1.hyundaicard.domain.card.Card;
import org.soptcollab.web1.hyundaicard.global.common.entity.BaseEntity;
import org.soptcollab.web1.hyundaicard.tag.Tag;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardTag extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "CARD_ID")
  private Card card; // CardTag 의 card 가 fk 와 매핑

  @ManyToOne
  @JoinColumn(name = "TAG_ID")
  private Tag tag; // CardTag 의 Tag 가 fk 와 매핑

  // Card 와 CardTag 는 양방향이므로, 연관관계 편의 메소드 설정
  public void linkCard(Card card){
    this.card = card;
    card.getCardTags().add(this);
  }

  // Tag 와 CardTag 는 일단 단방향으로 설정했으므로, 연관관계 편의 메소드는 추후 설정 고려


}
