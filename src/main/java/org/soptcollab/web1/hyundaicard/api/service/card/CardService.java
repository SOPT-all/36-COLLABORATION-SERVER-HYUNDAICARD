package org.soptcollab.web1.hyundaicard.api.service.card;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.api.service.card.dto.CardResponseDto;
import org.soptcollab.web1.hyundaicard.domain.card.Card;
import org.soptcollab.web1.hyundaicard.domain.card.CardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  public List<CardResponseDto> findAll() {

    return cardRepository.findAll().stream()
        .limit(15) //15개까지만 조회
        .map(card -> CardResponseDto.from(card))
        .collect(Collectors.toList());
  }
}
