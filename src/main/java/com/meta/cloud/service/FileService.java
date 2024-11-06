package com.meta.cloud.service;

import com.meta.cloud.domain.File;
import com.meta.cloud.domain.User;
import com.meta.cloud.dto.file.ListResponseDto;
import com.meta.cloud.dto.file.UploadResponseDto;
import com.meta.cloud.exception.FileDownloadException;
import com.meta.cloud.exception.FileStoreException;
import com.meta.cloud.exception.UserException;
import com.meta.cloud.repository.FileRepository;
import com.meta.cloud.repository.UserRepository;
import com.meta.cloud.util.S3BucketUtil;
import org.springframework.core.io.Resource;
import com.meta.cloud.util.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final S3BucketUtil s3BucketUtil;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Transactional
    public UploadResponseDto upload(MultipartFile file, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

        //파일 실제로 저장 -> s3에 저장으로 교체 예정
        File storedFile = s3BucketUtil.storeFile(file, user);

        user.increaseStorage(file.getSize());

        //파일에 대한 메타 데이터 db에 저장
        return new UploadResponseDto().toDto(fileRepository.save(storedFile));
    }

    // 파일 리스트 조회
    @Transactional(readOnly = true)
    public List<ListResponseDto> findByUserId(String id) {
        List<File> files = fileRepository.findByUser_Id(id);
        return files.stream()
                .map(file -> new ListResponseDto().toDto(file))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Resource download(String fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileDownloadException(ResponseCode.FILE_STORE_ERROR));

        String filePath = s3BucketUtil.getFullPath(file.getStoreFileName());
        return s3BucketUtil.loadFileAsResource(filePath, file.getUploadFileName());
    }
}
