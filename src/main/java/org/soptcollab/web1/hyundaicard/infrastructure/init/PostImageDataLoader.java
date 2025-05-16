package org.soptcollab.web1.hyundaicard.infrastructure.init;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.Image.Image;
import org.soptcollab.web1.hyundaicard.Image.ImageRepository;
import org.soptcollab.web1.hyundaicard.api.service.s3.S3Service;
import org.soptcollab.web1.hyundaicard.domain.post.Post;
import org.soptcollab.web1.hyundaicard.domain.post.PostRepository;
import org.soptcollab.web1.hyundaicard.global.util.LoggingUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class PostImageDataLoader implements CommandLineRunner {

  private final static String POST_THUMBNAIL_PATH = "post-thumbnails";
  private final S3Service s3Service;
  private final ImageRepository imageRepository;
  private final PostRepository postRepository;
  private final LoggingUtil loggingUtil;

  @Override
  @Transactional
  public void run(String... args) {
    if (postRepository.count() > 0) {
      return;
    }

    // 1) S3에서 card-thumbnails 디렉터리의 URL 리스트 가져오기
    List<String> urls = s3Service.listFiles(POST_THUMBNAIL_PATH);

    List<Image> images = urls.stream()
        .map(url -> {
          try {
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

            BufferedImage image = ImageIO.read(new URL(url));
            int width = image.getWidth();
            int height = image.getHeight();

            return Image.builder()
                .url(url)
                .extension(extension)
                .width(width)
                .height(height)
                .build();
          } catch (Exception e) {
            loggingUtil.error(e);
            loggingUtil.info("png 메타데이터 추출 실패: " + url);
            return null;
          }
        })
        .toList();

    imageRepository.saveAll(images);

    List<Image> sortedImages = images.stream()
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(img -> {
          String name = img.getUrl().substring(img.getUrl().lastIndexOf('/') + 1);
          String base = name.contains(".") ? name.substring(0, name.lastIndexOf('.')) : name;
          String numberStr = base.replaceAll("[^0-9]", ""); // 숫자만 추출
          return numberStr.isEmpty() ? 0 : Integer.parseInt(numberStr);
        }))
        .toList();

    List<String> dummyTitles = List.of(
        "현대카드 Boutique 신규 회원 연회비 캐시백 이벤트",
        "현대카드 Summit CE 신규 회원 연회비 캐시백 이벤트",
        "the Green Edition3 신규 회원 연회비 캐시백 이벤트",
        "the Pink Edition2 신규 회원 연회비 캐시백 이벤트",
        "현대카드 MX Black Edition2 신규 회원 연회비 캐시백 이벤트",
        "무신사 현대카드로 결제하면 최대 4만원 즉시 할인",
        "아메리칸 익스프레스 더 플래티넘 카드 럭셔리 브랜드 최대 10% 할인",
        "현대카드 Summit 신규 회원 연회비 캐시백 이벤트",
        "에너지플러스카드 Edition3 최대 15만 주유 쿠폰"
    );

    List<Post> posts = IntStream.range(0, Math.min(sortedImages.size(), dummyTitles.size()))
        .mapToObj(i -> {
          Image image = sortedImages.get(i);
          String title = dummyTitles.get(i);

          return Post.builder()
              .title(title)
              .image(image)
              .build();
        })
        .toList();

    postRepository.saveAll(posts);

    loggingUtil.info("이미지 기반 더미 게시물 생성 완료");
  }
}
