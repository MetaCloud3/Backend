package com.meta.cloud.service;

import com.meta.cloud.domain.File;
import com.meta.cloud.dto.file.UploadResponseDto;
import com.meta.cloud.repository.FileRepository;
import com.meta.cloud.util.S3BucketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3BucketUtil s3BucketUtil;
    private final FileRepository fileRepository;

    @Transactional
    public UploadResponseDto upload(MultipartFile file) {
        //파일 실제로 저장 -> s3에 저장으로 교체 예정
        File storedFile = s3BucketUtil.storeFile(file);

        //파일에 대한 메타 데이터 db에 저장
        return new UploadResponseDto().toDto(fileRepository.save(storedFile));
    }
}
