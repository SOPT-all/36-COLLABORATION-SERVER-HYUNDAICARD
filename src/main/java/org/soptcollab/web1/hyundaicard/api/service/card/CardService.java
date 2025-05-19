package org.soptcollab.web1.hyundaicard.api.service.card;

import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardBrandGroupDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardResponseDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardSearchRequestDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardSearchResponseDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardSearchResponseDto.RecommendationCategoriesDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardSearchResponseDto.SearchedCardDto;
import org.soptcollab.web1.hyundaicard.domain.card.Brand;
import org.soptcollab.web1.hyundaicard.domain.card.Card;
import org.soptcollab.web1.hyundaicard.domain.card.CardRepository;
import org.soptcollab.web1.hyundaicard.global.common.enums.ErrorCode;
import org.soptcollab.web1.hyundaicard.global.error.exception.ApiException;
import org.soptcollab.web1.hyundaicard.tag.Tag;
import org.soptcollab.web1.hyundaicard.tag.TagRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

	private final CardRepository cardRepository;
	private final TagRepository tagRepository;

	public List<CardBrandGroupDto> findGroupedCards() {
		List<Card> cards = cardRepository.findAll();

		// 브랜드별로 다른 조건으로 필터링
		Map<String, List<Card>> filteredGrouped = cards.stream()
			.filter(card -> {
				int height = card.getImage().getHeight();
				Brand brand = card.getBrand();
				if (brand == Brand.AMERICAN_EXPRESS) {
					return height <= 200; // 가로만
				} else {
					return height > 200;  // 세로만
				}
			})
			.collect(Collectors.groupingBy(card -> card.getBrand().name()));

		// 브랜드별 개수 제한
		Map<String, Integer> brandLimits = Map.of(
			"HYUNDAI_ORIGINALS", 3,
			"AMERICAN_EXPRESS", 3,
			"CHAMPION_BRANDS", 8
		);

		return brandLimits.entrySet().stream()
			.map(entry -> {
				String brand = entry.getKey();
				int limit = entry.getValue();

				List<CardResponseDto> dtos = filteredGrouped.getOrDefault(brand, List.of())
					.stream()
					.limit(limit)
					.map(CardResponseDto::from)
					.toList();

				return new CardBrandGroupDto(brand, dtos);
			})
			.toList();
	}

	/**
	 * 검색 조건에 따라 카드 검색
	 *
	 * @param cardSearchRequestDto card 검색 조건 dto
	 * @return 카드 검색 응답 dto
	 */
	public CardSearchResponseDto searchCards(
		final CardSearchRequestDto cardSearchRequestDto) {

		List<String> tagIds = cardSearchRequestDto.filters().tagIds();

		// 메인 카드는 이미지가 세로인 카드에서만 검색
		// 추천 카드는 이미지가 가로인 카드에서만 검색
		Card mainCard = fetchMainCard(tagIds);
		List<Card> recommendedCards = fetchRecommendedCards(tagIds);
		List<Tag> selectedTags = fetchSelectedTag(tagIds);

		return getCardSearchResponseDto(mainCard, selectedTags, recommendedCards);
	}

	private List<Tag> fetchSelectedTag(final List<String> tagIds) {
		return tagRepository.findAllByIdIn(tagIds);
	}

	/**
	 * 선택한 태그 아이디를 가장 많이 가지는 메인 카드 fetch
	 *
	 * @param tagIds 선택 태그 아이디
	 * @return 메인 카드
	 */
	private Card fetchMainCard(final List<String> tagIds) {
		Tuple mainCardResult = cardRepository.getVerticalCardOrderByTags(tagIds)
			.orElseThrow(() -> new ApiException(
				ErrorCode.INTERNAL_SERVER_ERROR));

		return mainCardResult.get("cardResult", Card.class);
	}

	/**
	 * 선택한 태그 아이디 기준으로 추천카드 fetch
	 *
	 * @param tagIds 선택 태그 아이디
	 * @return 카드 리스트
	 */
	private List<Card> fetchRecommendedCards(final List<String> tagIds) {
		List<Tuple> cardResults = cardRepository.getHorizontalCardOrderByTags(tagIds,
			PageRequest.of(0, 2));

		return cardResults.stream()
			.map(cardResult -> cardResult.get("cardResult", Card.class))
			.toList();
	}

	/**
	 * @param mainCard         메인카드
	 * @param recommendedCards 추천 카드
	 * @return 응답 dto
	 */
	private CardSearchResponseDto getCardSearchResponseDto(
		final Card mainCard,
		final List<Tag> tags,
		final List<Card> recommendedCards
	) {

		return CardSearchResponseDto.builder()
			.mainCard(
				SearchedCardDto.from(mainCard)
			)
			.recommendationCategoriesDto(
				RecommendationCategoriesDto.from(tags)
			)
			.otherRecommendationCards(
				recommendedCards.stream()
					.map(SearchedCardDto::from)
					.toList()
			)
			.build();
	}

}





