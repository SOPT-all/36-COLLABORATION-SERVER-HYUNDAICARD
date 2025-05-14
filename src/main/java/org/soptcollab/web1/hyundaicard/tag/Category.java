package org.soptcollab.web1.hyundaicard.tag;

public enum Category {

  SHOPPING_CONSUMPTION("쇼핑/소비"), // 쇼핑/소비
  TRAVEL_GLOBAL("여행/글로벌"), // 여행/글로벌
  MOVEMENT_TRANSPORTATION("이동/교통"), // 이동/교통
  LIFESTYLE_CONVENIENCE("생활/편의"), // 생활/편의
  FINANCE_BUSINESS("금융/사업");// 금융/사업

  private final String type;

  Category(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

}
