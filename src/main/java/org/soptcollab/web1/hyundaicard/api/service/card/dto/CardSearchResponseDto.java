package org.soptcollab.web1.hyundaicard.api.service.card.dto;

import java.util.List;
import lombok.Builder;
import org.soptcollab.web1.hyundaicard.domain.card.Card;
import org.soptcollab.web1.hyundaicard.domain.card.PaymentNetwork;
import org.soptcollab.web1.hyundaicard.tag.Tag;

@Builder
public record CardSearchResponseDto(
    SearchedCardDto mainCard,
    RecommendationCategoriesDto recommendationCategoriesDto,
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

      final String buttonNode = "신규 회원, 연회비 캐시백 \n"
          + "이벤트 진행 중";

      return SearchedCardDto.builder()
          .id(card.getId())
          .name(card.getName())
          .paymentNetwork(card.getPaymentNetwork())
          .imageUrl(card.getImage().getUrl())
          .buttonNode(buttonNode)
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

  @Builder
  public record RecommendationCategoriesDto(
      SelectedFilters selectedFilters,
      RecommendedStyle recommendedStyle
  ) {

    public static RecommendationCategoriesDto from(List<Tag> tags) {
      return RecommendationCategoriesDto.builder()
          .selectedFilters(
              SelectedFilters.builder()
                  .title("라이프스타일 / 여행")
                  .description("내가 선택한 필터")
                  .tags(tags.stream().map(Tag::getName).toList())
                  .build()
          )
          .recommendedStyle(
              RecommendedStyle.builder()
                  .title("가지고는 여행을 위한 라이프스타일 카드")
                  .description("나는 어떤 카드가 어울릴까?")
                  .additionalInfo("단순히 지출이 아닌 가치 있는 경험! 프리미엄 라이프스타일을 더해주는 Boutique - Velvet 카드")
                  .build()
          )
          .build();
    }

    @Builder
    public record SelectedFilters(
        String title,
        String icon,
        String description,
        List<String> tags
    ) {

    }

    @Builder
    public record RecommendedStyle(
        String title,
        String icon,
        String description,
        String additionalInfo
    ) {

    }
  }
}
