package org.soptcollab.web1.hyundaicard.api.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class S3Service {

  private final AmazonS3 amazonS3;
  private final String bucket;

  public S3Service(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket) {
    this.amazonS3 = amazonS3;
    this.bucket = bucket;

  }

  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    // 파일 이름에서 공백을 제거한 새로운 파일 이름 생성
    String originalFileName = multipartFile.getOriginalFilename();

    // UUID를 파일명에 추가
    String uuid = UUID.randomUUID().toString();
    String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\\\s", "_");

    String fileName = dirName + "/" + uniqueFileName;
    log.info("fileName: " + fileName);
    File uploadFile = convert(multipartFile);

    String uploadImageUrl = putS3(uploadFile, fileName);
    removeNewFile(uploadFile);
    return uploadImageUrl;

  }

  private File convert(MultipartFile file) throws IOException {
    String originalFileName = file.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\\\s", "_");

    File convertFile = new File(uniqueFileName);
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      } catch (IOException e) {
        log.error("파일 변환 중 오류 발생: {}", e.getMessage());
        throw e;
      }
      return convertFile;
    }
    throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다. %s", originalFileName));
  }

  private String putS3(File uploadFile, String fileName) {
    amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
        .withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3.getUrl(bucket, fileName).toString();
  }

  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("파일이 삭제되었습니다.");
    } else {
      log.info("파일이 삭제되지 못했습니다.");
    }
  }

  public void deleteFile(String fileName) {
    try {
      // URL 디코딩을 통해 원래의 파일 이름을 가져옵니다.
      String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
      log.info("Deleting file from S3: " + decodedFileName);
      amazonS3.deleteObject(bucket, decodedFileName);
    } catch (UnsupportedEncodingException e) {
      log.error("Error while decoding the file name: {}", e.getMessage());
    }
  }

  public String updateFile(MultipartFile newFile, String oldFileName, String dirName)
      throws IOException {
    // 기존 파일 삭제
    log.info("S3 oldFileName: " + oldFileName);
    deleteFile(oldFileName);
    // 새 파일 업로드
    return upload(newFile, dirName);
  }

  /**
   * 1) 단일 파일을 S3에서 가져오기
   *
   * @param fileName S3에 저장된 전체 경로
   * @return S3Object (InputStream 등을 직접 사용 가능)
   */
  public S3Object getFile(String fileName) {

    log.info("Fetching file from S3: {}", fileName);

    return amazonS3.getObject(bucket, fileName);
  }

  /**
   * 2) 특정 디렉터리의 모든 파일 URL 목록 조회
   *
   * @param dirName 폴더 경로
   * @return 접근 가능한 URL 리스트
   */
  public List<String> listFiles(String dirName) {
    ListObjectsV2Request req = new ListObjectsV2Request()
        .withBucketName(bucket)
        .withPrefix(dirName + "/");
    ListObjectsV2Result result = amazonS3.listObjectsV2(req);

    return result.getObjectSummaries()
        .stream()
        .map(S3ObjectSummary::getKey)
        .filter(key -> !key.equals(dirName + "/"))
        .map(key -> amazonS3.getUrl(bucket, key).toString())
        .collect(Collectors.toList());
  }


}
