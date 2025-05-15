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
        Tag.builder()
            .name("온라인 쇼핑")
            .code("ONLINE_SHOPPING")
            .category(Category.SHOPPING_CONSUMPTION)        // 쇼핑/소비
            .displayOrder(1)
            .hoverText("온라인 쇼핑몰 결제 시 추가 적립")
            .build(),

        Tag.builder()
            .name("여행")
            .code("TRAVEL")
            .category(Category.TRAVEL_GLOBAL)               // 여행/글로벌
            .displayOrder(2)
            .hoverText("국내 여행 관련 혜택")
            .build(),

        Tag.builder()
            .name("차량 관리")
            .code("VEHICLE_MAINTENANCE")
            .category(Category.MOVEMENT_TRANSPORTATION)      // 이동/교통
            .displayOrder(3)
            .hoverText("차량 정비·검사 시 혜택")
            .build(),

        Tag.builder()
            .name("커피")
            .code("COFFEE")
            .category(Category.LIFESTYLE_CONVENIENCE)       // 생활/편의
            .displayOrder(4)
            .hoverText("카페 이용 시 적립 또는 할인")
            .build(),

        Tag.builder()
            .name("개인사업자")
            .code("SELF_EMPLOYED")
            .category(Category.FINANCE_BUSINESS)            // 금융/사업
            .displayOrder(5)
            .hoverText("개인사업자 결제 시 전용 혜택")
            .build()

    );

    tagRepository.saveAll(dummyTags);

  }
}
