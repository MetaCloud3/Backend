package com.meta.cloud.util;

import com.meta.cloud.domain.File;
import com.meta.cloud.domain.FileType;
import com.meta.cloud.domain.User;
import com.meta.cloud.exception.FileStoreException;
import com.meta.cloud.util.api.ResponseCode;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

/**
 * FileStore 클래스 위치 리팩토링 필요
 */
@Component
public class S3BucketUtil {

    private final S3Client s3Client1;
    private final S3Client s3Client2;

    @Value("${file.dir}")
    private String fileDir;

    @Value("${aws.s3.bucketName1}")
    private String bucketName1;

    @Value("${aws.s3.bucketName2}")
    private String bucketName2;

    public S3BucketUtil(@Qualifier("s3Client1") S3Client s3Client1,
                        @Qualifier("s3Client2") S3Client s3Client2) {
        this.s3Client1 = s3Client1;
        this.s3Client2 = s3Client2;
    }

    //파일 분할


    //파일 분할 업로드
    public File storeFile(MultipartFile multipartFile, User user) {
        if(multipartFile.isEmpty()) {
            return null;
        }
        String uploadFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);

        String storeFileNamePart1 = storeFileName + "_part1";
        String storeFileNamePart2 = storeFileName + "_part2";

        try {
            //파일 두 조각 분할
            byte[] fileBytes = multipartFile.getBytes();
            int mid = multipartFile.getBytes().length / 2;

            byte[] part1 = Arrays.copyOfRange(fileBytes, 0, mid);
            byte[] part2 = Arrays.copyOfRange(fileBytes, mid, fileBytes.length);

            // 첫 번째 조각을 첫 번째 S3 버킷에 업로드
            s3Client1.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName1)
                        .key(storeFileNamePart1)
                        .build(),
                    RequestBody.fromBytes(part1)
            );

            s3Client2.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName2)
                        .key(storeFileNamePart2)
                        .build(),
                    RequestBody.fromBytes(part2)
            );
        } catch (IOException e) {
            throw new FileStoreException(ResponseCode.FILE_STORE_ERROR);
        }
        return new File(uploadFileName, storeFileName, getFileType(multipartFile.getContentType()), multipartFile.getSize(), null, user);
    }

    //파일 저장 위치(로컬)
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    //서버에 저장할 파일명 생성
    private String createStoreFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "." + extractExt(originalFilename);
    }

    // 로컬에 저장된 파일을 Resource로 반환하는 메서드
    public Resource loadFileAsResource(String storeFileName, String uploadFileName) {
        try {
            String storeFileNamePart1 = storeFileName + "_part1";
            String storeFileNamePart2 = storeFileName + "_part2";

            // 첫 번째 조각을 첫 번째 S3 버킷에서 가져오기
            ResponseInputStream<GetObjectResponse> s3ObjectPart1 = s3Client1.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName1)
                            .key(storeFileNamePart1)
                            .build()
            );

            // 두 번째 조각을 두 번째 S3 버킷에서 가져오기
            ResponseInputStream<GetObjectResponse> s3ObjectPart2 = s3Client2.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName2)
                            .key(storeFileNamePart2)
                            .build()
            );

            // 두 조각을 임시 파일로 결합
            Path tempFile = Files.createTempFile("download-", "-" + uploadFileName);
            try (OutputStream outputStream = Files.newOutputStream(tempFile)) {
                IOUtils.copy(s3ObjectPart1, outputStream);
                IOUtils.copy(s3ObjectPart2, outputStream);
            }

            return new UrlResource(tempFile.toUri());
        } catch (Exception e) {
            throw new RuntimeException("파일 다운로드 실패", e);
        }
    }

    //파일 확장자 추출
    private String extractExt(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    //파일 타입 결정
    private FileType getFileType(String contentType) {
        if (contentType == null) {
            return FileType.OTHER;
        }
        if (contentType.startsWith("image")) {
            return FileType.IMAGE;
        } else if (contentType.startsWith("video")) {
            return FileType.VIDEO;
        } else if (contentType.startsWith("audio")) {
            return FileType.AUDIO;
        } else if (contentType.startsWith("application") || contentType.startsWith("text")) {
            return FileType.DOCUMENT;
        } else {
            return FileType.OTHER;
        }
    }
}
