package org.soptcollab.web1.hyundaicard.api.service.card.dto;

import org.soptcollab.web1.hyundaicard.domain.card.Card;

public record CardResponseDto(
    String imageUrl,//"{이미지 url}",
    String name, //"the Red"
    String description, //"현대카드의 오리지널리티를 담은 카드"
    String brand //"Hyundai Originals"
) {

  public static CardResponseDto from(Card c){
    return new CardResponseDto(c.getImage().getUrl(), c.getName(), c.getBrand().getSlogan(),
        c.getBrand().name());
  }

}
