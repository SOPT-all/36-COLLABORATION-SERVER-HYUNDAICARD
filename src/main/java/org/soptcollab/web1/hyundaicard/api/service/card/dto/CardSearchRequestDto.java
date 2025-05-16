package org.soptcollab.web1.hyundaicard.api.service.card.dto;

import java.util.List;

public record CardSearchRequestDto(
    Filters filters
) {

  public record Filters(
      List<String> tagIds,
      AnnualFeeRange annualFeeRange
  ) {

  }

  public record AnnualFeeRange(
      Integer min,
      Integer max
  ) {

  }

}
