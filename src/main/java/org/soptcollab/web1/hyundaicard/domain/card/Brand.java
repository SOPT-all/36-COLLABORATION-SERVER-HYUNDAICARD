package org.soptcollab.web1.hyundaicard.domain.card;

public enum Brand{

  HYUNDAI_ORIGINALS("현대카드의 오리지널리티를 담은 진정한 현대카드"),
  AMERICAN_EXPRESS("don't live life without it"),
  CHAMPION_BRANDS("최고의 브랜드와 콜라보한 또 하나의 현대 카드");

  private final String slogan;

  Brand(String slogan) {
    this.slogan = slogan;
  }

  public String getSlogan() {
    return slogan;
  }


}