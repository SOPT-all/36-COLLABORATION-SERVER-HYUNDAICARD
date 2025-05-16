package org.soptcollab.web1.hyundaicard.api.service.card.dto;

import java.util.List;
import lombok.Builder;
import org.soptcollab.web1.hyundaicard.domain.card.Card;
import org.soptcollab.web1.hyundaicard.domain.card.PaymentNetwork;

@Builder
public record CardSearchResponseDto(
    SearchedCardDto mainCard,
    List<SearchedCardDto> otherRecommendationCards
) {

  @Builder
  public record SearchedCardDto(
      String id,
      String name,
      String imageUrl,
      PaymentNetwork paymentNetwork,
      List<CardBenefitDto> benefits,
      String buttonNode
  ) {

    public static SearchedCardDto from(Card card) {
      return SearchedCardDto.builder()
          .id(card.getId())
          .name(card.getName())
          .paymentNetwork(card.getPaymentNetwork())
          .imageUrl(card.getImage().getUrl())
          .buttonNode("test")
          .benefits(
              List.of(CardBenefitDto.dummyDto())
          )
          .build();
    }

  }

  @Builder
  public record CardBenefitDto(
      String title,
      String details
  ) {

    public static CardBenefitDto dummyDto() {
      return CardBenefitDto.builder()
          .title("해외 여행 시 혜택 강화 ")
          .details("해외 결제 시 우대 환율 적용과 여행자 보험이 포함되어 있어 자주 여행하는 \n"
              + "사람에게 경제적이고 안전한 선택")
          .build();
    }
  }
}
