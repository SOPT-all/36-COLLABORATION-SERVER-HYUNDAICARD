package org.soptcollab.web1.hyundaicard.api.service.card;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardBrandGroupDto;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardResponseDto;
import org.soptcollab.web1.hyundaicard.domain.card.Card;
import org.soptcollab.web1.hyundaicard.domain.card.CardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  public List<CardBrandGroupDto> findUpTo15() {

    List<CardResponseDto> allCards = cardRepository.findAll().stream()
        .limit(15) // 최대 15개까지만 조회
        .map(CardResponseDto::from)
        .toList();

    // Car
    Map<String, List<CardResponseDto>> groupedByBrand = allCards.stream()
        .collect(Collectors.groupingBy(CardResponseDto::brand));

    return groupedByBrand.entrySet().stream()
        .map(entry -> new CardBrandGroupDto(entry.getKey(), entry.getValue()))
        .toList();
  }
}





