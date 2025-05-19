package org.soptcollab.web1.hyundaicard.api.controller.card;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.soptcollab.web1.hyundaicard.api.service.card.CardService;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardBrandGroupDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardSearchRequestDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardSearchResponseDto;
import org.soptcollab.web1.hyundaicard.global.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
@Tag(name = " 카드 api", description = "카드 리스트, 카드 검색 api")
public class CardController {

	private final CardService cardService;

	/**
	 * @return 메인페이지의 카드 개수가 15개인 것을 고려하여 15개의 카드 정보를 반환합니다.
	 */
	@GetMapping
	@Operation(summary = "카드 리스트", description = "메인페이지의 카드 개수가 15개인 것을 고려하여 15개의 카드 정보를 반환합니다.")
	public ResponseEntity<ApiResponse<List<CardBrandGroupDto>>> getCardList() {

		List<CardBrandGroupDto> cards = cardService.findGroupedCards();

		return ResponseEntity.ok(ApiResponse.success(cards));
	}

	@PostMapping("/search")
	@Operation(summary = "카드 검색",
		description = "카드 검색 페이지에서 검색 결과를 반환합니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(mediaType = "application/json")
		)
	)
	public ResponseEntity<ApiResponse<CardSearchResponseDto>> searchCards(
		@RequestBody CardSearchRequestDto cardSearchRequestDto) {

		return ResponseEntity.ok(ApiResponse.success(cardService.searchCards(cardSearchRequestDto)));
	}
}


