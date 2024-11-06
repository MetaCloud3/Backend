package com.meta.cloud.util;

import com.meta.cloud.domain.File;
import com.meta.cloud.domain.FileType;
import com.meta.cloud.domain.User;
import com.meta.cloud.exception.FileStoreException;
import com.meta.cloud.util.api.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * FileStore 클래스 위치 리팩토링 필요
 */
@Component
public class S3BucketUtil {

    @Value("${file.dir}")
    private String fileDir;

    //파일 로컬에 저장 -> s3 저장으로 교체 예정
    public File storeFile(MultipartFile multipartFile, User user) {
        if(multipartFile.isEmpty()) {
            return null;
        }
        String uploadFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);
        try {
            multipartFile.transferTo(new java.io.File(getFullPath(storeFileName)));
        } catch (IOException e) {
            throw new FileStoreException(ResponseCode.FILE_STORE_ERROR);
        }
        return new File(uploadFileName, storeFileName, getFileType(multipartFile.getContentType()), multipartFile.getSize(), fileDir, user);
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
    public Resource loadFileAsResource(String filePath, String originalFileName) {
        try {
            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("파일을 찾을 수 없습니다: " + originalFileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("파일을 로드할 수 없습니다.", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
