package org.soptcollab.web1.hyundaicard.api.service.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class S3Service {

  private final S3Client s3Client;
  private final String bucket;
  private final String region;

  public S3Service(S3Client s3Client,
      @Value("${cloud.aws.s3.bucket}") String bucket,
      @Value("${cloud.aws.region.static}") String region) {
    this.s3Client = s3Client;
    this.bucket = bucket;
    this.region = region;
  }

  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    String originalFileName = multipartFile.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s+", "_");

    String fileName = dirName + "/" + uniqueFileName;
    File uploadFile = convert(multipartFile);

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucket)
        .key(fileName)
        .acl(ObjectCannedACL.PUBLIC_READ)
        .contentType(multipartFile.getContentType())
        .build();

    s3Client.putObject(putObjectRequest, RequestBody.fromFile(uploadFile));

    removeNewFile(uploadFile);
    return getObjectUrl(fileName);
  }

  private File convert(MultipartFile file) throws IOException {
    String originalFileName = file.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();
    String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s+", "_");

    File convertFile = new File(uniqueFileName);
    try (FileOutputStream fos = new FileOutputStream(convertFile)) {
      fos.write(file.getBytes());
    }
    return convertFile;
  }

  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("임시 파일 삭제 성공");
    } else {
      log.warn("임시 파일 삭제 실패");
    }
  }

  public void deleteFile(String fileName) {
    String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
    s3Client.deleteObject(DeleteObjectRequest.builder()
        .bucket(bucket)
        .key(decodedFileName)
        .build());
  }

  public String updateFile(MultipartFile newFile, String oldFileName, String dirName)
      throws IOException {
    deleteFile(oldFileName);
    return upload(newFile, dirName);
  }

  public byte[] getFile(String fileName) {
    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
        .bucket(bucket)
        .key(fileName)
        .build();

    ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(getObjectRequest);
    return response.asByteArray(); // 필요 시 InputStream으로도 변환 가능
  }

  public List<String> listFiles(String dirName) {
    ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
        .bucket(bucket)
        .prefix(dirName + "/")
        .build();

    ListObjectsV2Response result = s3Client.listObjectsV2(listRequest);

    return result.contents().stream()
        .map(S3Object::key)
        .filter(key -> !key.equals(dirName + "/"))
        .map(this::getObjectUrl)
        .collect(Collectors.toList());
  }

  private String getObjectUrl(String key) {
    return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
  }
}
