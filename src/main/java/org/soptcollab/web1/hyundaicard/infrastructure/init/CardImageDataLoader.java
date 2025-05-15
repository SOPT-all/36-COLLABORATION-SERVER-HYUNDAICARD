package org.soptcollab.web1.hyundaicard.infrastructure.init;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.soptcollab.web1.hyundaicard.Image.Image;
import org.soptcollab.web1.hyundaicard.Image.ImageRepository;
import org.soptcollab.web1.hyundaicard.api.service.s3.S3Service;
import org.soptcollab.web1.hyundaicard.domain.card.Brand;
import org.soptcollab.web1.hyundaicard.domain.card.Card;
import org.soptcollab.web1.hyundaicard.domain.card.CardRepository;
import org.soptcollab.web1.hyundaicard.domain.card.PaymentNetwork;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Configuration
@RequiredArgsConstructor
public class CardImageDataLoader implements CommandLineRunner {

  private final S3Service s3Service;
  private final ImageRepository imageRepository;
  private final CardRepository cardRepository;

  @Override
  public void run(String... args) {
    if (cardRepository.count() > 0) {
      return;
    }

    // 1) S3에서 card-thumbnails 디렉터리의 URL 리스트 가져오기
    List<String> urls = s3Service.listFiles("card-thumbnails");

    // 2) Image 엔티티로 저장
    List<Image> images = urls.stream()
        .map(url -> {
          try {
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

            Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new URL(url).openStream());

            Element svgElement = doc.getDocumentElement();

            String widthStr = svgElement.getAttribute("width");
            String heightStr = svgElement.getAttribute("height");

            int width = parseSvgSize(widthStr);
            int height = parseSvgSize(heightStr);

            return Image.builder()
                .url(url)
                .extension(extension)
                .width(width)
                .height(height)
                .build();
          } catch (Exception e) {
            System.err.println("SVG 메타데이터 추출 실패: " + url);
            return null;
          }
        })
        .filter(Objects::nonNull)
        .toList();

    imageRepository.saveAll(images);

    // 3) Brand, PaymentNetwork 순환 배열 준비
    Brand[] brands = Brand.values();
    PaymentNetwork[] networks = PaymentNetwork.values();

    // 4) Image 리스트 인덱스를 활용해 Card 더미 생성
    List<Card> cards = IntStream.range(0, images.size())
        .mapToObj(idx -> {
          Image img = images.get(idx);

          // URL에서 파일명 → displayName 생성
          String fileName = img.getUrl().substring(img.getUrl().lastIndexOf('/') + 1);
          String displayName = fileName.replace("." + img.getExtension(), "");
//          String displayName = base.replace('_', ' ');

          Brand brand = brands[idx % brands.length];
          PaymentNetwork network = networks[idx % networks.length];
          String benefits = brand.getSlogan();
          String buttonNote = "지금 신청하기";

          return Card.builder()
              .name(displayName)
              .brand(brand)
              .paymentNetwork(network)
              .benefits(benefits)
              .buttonNote(buttonNote)
              .image(img)
              .build();
        })
        .toList();
    cardRepository.saveAll(cards);

    System.out.println(">>> S3 기반 더미 Card & Image 로드 완료!");
  }

  private static int parseSvgSize(String sizeStr) {
    if (sizeStr == null || sizeStr.isEmpty()) return 0;
    return (int) Double.parseDouble(sizeStr.replaceAll("[^0-9.]", ""));
  }

}
