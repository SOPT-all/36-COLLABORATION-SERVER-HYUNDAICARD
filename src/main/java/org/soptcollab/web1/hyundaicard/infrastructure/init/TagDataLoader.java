package org.soptcollab.web1.hyundaicard.infrastructure.init;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.tag.Category;
import org.soptcollab.web1.hyundaicard.tag.Tag;
import org.soptcollab.web1.hyundaicard.tag.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TagDataLoader implements CommandLineRunner {

  private final TagRepository tagRepository;

  @Override
  public void run(String... args) throws Exception {
    if (tagRepository.count() > 0) {
      return;  // 이미 데이터가 있으면 스킵
    }

    List<Tag> dummyTags = List.of(
        // 쇼핑/소비
        Tag.builder().name("온라인 쇼핑").code("ONLINE_SHOPPING").category(Category.SHOPPING_CONSUMPTION)
            .displayOrder(1).hoverText("온라인 쇼핑몰 결제 시 추가 적립").build(),
        Tag.builder().name("온라인 페이").code("ONLINE_PAY").category(Category.SHOPPING_CONSUMPTION)
            .displayOrder(2).hoverText("간편결제 시 할인 또는 적립").build(),
        Tag.builder().name("크레딧").code("CREDIT").category(Category.SHOPPING_CONSUMPTION)
            .displayOrder(3).hoverText("현대카드 크레딧 사용 혜택").build(),
        Tag.builder().name("바우처").code("VOUCHER").category(Category.SHOPPING_CONSUMPTION)
            .displayOrder(4).hoverText("특정 제휴처 바우처 사용 가능").build(),
        Tag.builder().name("어디서나 적립/할인").code("EVERYWHERE_REWARD")
            .category(Category.SHOPPING_CONSUMPTION).displayOrder(5).hoverText("전 가맹점 적립 또는 할인 제공")
            .build(),
        Tag.builder().name("오프라인 쇼핑").code("OFFLINE_SHOPPING")
            .category(Category.SHOPPING_CONSUMPTION).displayOrder(6).hoverText("백화점·마트 등 오프라인 혜택")
            .build(),
        Tag.builder().name("디지털 콘텐츠").code("DIGITAL_CONTENT")
            .category(Category.SHOPPING_CONSUMPTION).displayOrder(7)
            .hoverText("음원·영상 등 콘텐츠 결제 시 혜택").build(),
        Tag.builder().name("배달 앱").code("DELIVERY_APP").category(Category.SHOPPING_CONSUMPTION)
            .displayOrder(8).hoverText("배달앱 결제 시 할인 또는 적립").build(),

        // 여행/글로벌
        Tag.builder().name("여행").code("TRAVEL").category(Category.TRAVEL_GLOBAL).displayOrder(9)
            .hoverText("국내 여행 관련 혜택").build(),
        Tag.builder().name("해외").code("OVERSEAS").category(Category.TRAVEL_GLOBAL).displayOrder(10)
            .hoverText("해외 이용 시 환율 우대 및 적립").build(),
        Tag.builder().name("마일리지/공항라운지").code("MILEAGE_LOUNGE").category(Category.TRAVEL_GLOBAL)
            .displayOrder(11).hoverText("마일리지 적립 및 라운지 이용 가능").build(),
        Tag.builder().name("특급호텔/멀티패키징").code("HOTEL_PACKAGE").category(Category.TRAVEL_GLOBAL)
            .displayOrder(12).hoverText("호텔 숙박 및 여행 패키지 할인").build(),

        // 이동/교통
        Tag.builder().name("차량 관리").code("VEHICLE_MAINTENANCE")
            .category(Category.MOVEMENT_TRANSPORTATION).displayOrder(13).hoverText("차량 정비·검사 시 혜택")
            .build(),
        Tag.builder().name("주유").code("FUEL").category(Category.MOVEMENT_TRANSPORTATION)
            .displayOrder(14).hoverText("주유소 결제 시 리워드 제공").build(),
        Tag.builder().name("이동수단").code("TRANSPORT").category(Category.MOVEMENT_TRANSPORTATION)
            .displayOrder(15).hoverText("대중교통·택시 결제 시 혜택").build(),

        // 생활/편의
        Tag.builder().name("커피").code("COFFEE").category(Category.LIFESTYLE_CONVENIENCE)
            .displayOrder(16).hoverText("카페 이용 시 적립 또는 할인").build(),
        Tag.builder().name("생활 서비스").code("LIFE_SERVICE").category(Category.LIFESTYLE_CONVENIENCE)
            .displayOrder(17).hoverText("세탁·청소 등 생활 서비스 할인").build(),
        Tag.builder().name("멤버십 혜택").code("MEMBERSHIP").category(Category.LIFESTYLE_CONVENIENCE)
            .displayOrder(18).hoverText("제휴 멤버십 혜택 제공").build(),

        // 금융/사업
        Tag.builder().name("금융").code("FINANCE").category(Category.FINANCE_BUSINESS)
            .displayOrder(19).hoverText("금융상품 결제 시 혜택 제공").build(),
        Tag.builder().name("렌탈").code("RENTAL").category(Category.FINANCE_BUSINESS).displayOrder(20)
            .hoverText("렌탈 서비스 이용 시 할인").build(),
        Tag.builder().name("개인사업자").code("SELF_EMPLOYED").category(Category.FINANCE_BUSINESS)
            .displayOrder(21).hoverText("개인사업자 결제 시 전용 혜택").build()
    );

    tagRepository.saveAll(dummyTags);
  }
}
