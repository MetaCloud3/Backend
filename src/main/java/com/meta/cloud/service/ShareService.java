package com.meta.cloud.service;

import com.meta.cloud.domain.File;
import com.meta.cloud.domain.Share;
import com.meta.cloud.domain.User;
import com.meta.cloud.dto.file.ListResponseDto;
import com.meta.cloud.dto.share.ShareRequestDto;
import com.meta.cloud.dto.share.ShareResponseDto;
import com.meta.cloud.exception.ShareException;
import com.meta.cloud.exception.UserException;
import com.meta.cloud.repository.FileRepository;
import com.meta.cloud.repository.ShareRepository;
import com.meta.cloud.repository.UserRepository;
import com.meta.cloud.util.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepository shareRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    //파일 공유
    @Transactional
    public ShareResponseDto shareFile(ShareRequestDto shareRequestDto, String ownId) {
        System.out.println("shareRequestDto.getSharedLoginId() = " + shareRequestDto.getSharedLoginId());
        return new ShareResponseDto().toDto(shareRepository.save(Share.builder()
                        .file(fileRepository.findById(shareRequestDto.getFileId()).orElseThrow(() -> new ShareException(ResponseCode.BAD_REQUEST)))
                        .ownUser(userRepository.findById(ownId).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND)))
                        .sharedUser(userRepository.findByLoginId(shareRequestDto.getSharedLoginId()).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND)))
                        .build()));
    }

    //공유받은 파일 리스트
    @Transactional(readOnly = true)
    public List<ListResponseDto> getShared(String sharedId) {
        User sharedUser = userRepository.findById(sharedId)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return shareRepository.findBySharedUser(sharedUser)
                .stream()
                .map(share -> new ListResponseDto().toDto(share.getFile()))
                .collect(Collectors.toList());
    }

    //공유한 파일 리스트
    @Transactional(readOnly = true)
    public List<ListResponseDto> getOwn(String ownId) {
        User ownUser = userRepository.findById(ownId)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return shareRepository.findByOwnUser(ownUser)
                .stream()
                .map(share -> new ListResponseDto().toDto(share.getFile()))
                .collect(Collectors.toList());
    }

    //파일 공유 삭제
    @Transactional
    public void deleteShare(String shareId) {
        shareRepository.deleteById(shareId);
    }
}
