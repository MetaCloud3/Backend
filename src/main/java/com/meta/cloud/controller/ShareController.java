package com.meta.cloud.controller;

import com.meta.cloud.dto.file.ListResponseDto;
import com.meta.cloud.dto.share.ShareRequestDto;
import com.meta.cloud.dto.share.ShareResponseDto;
import com.meta.cloud.service.FileService;
import com.meta.cloud.service.ShareService;
import com.meta.cloud.util.api.ApiResponse;
import com.meta.cloud.util.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/share")
@RequiredArgsConstructor
@Slf4j
public class ShareController {

    private final ShareService shareService;
    private final FileService fileService;

    //파일 공유
    @PostMapping("/")
    public ApiResponse<ShareResponseDto> shareFile(@RequestBody ShareRequestDto shareRequestDto, Authentication authentication) {
        return ApiResponse.success(shareService.shareFile(shareRequestDto, authentication.getName()), ResponseCode.FILE_LIST_SUCCESS.getMessage());
    }

    //공유한 파일 리스트
    @GetMapping("/own")
    public ApiResponse<List<ListResponseDto>> getOwn(Authentication authentication) {
        return ApiResponse.success(shareService.getOwn(authentication.getName()), ResponseCode.FILE_LIST_SUCCESS.getMessage());
    }

    //공유받은 파일 리스트
    @GetMapping("/shared")
    public ApiResponse<List<ListResponseDto>> getShared(Authentication authentication) {
        return ApiResponse.success(shareService.getShared(authentication.getName()), ResponseCode.FILE_LIST_SUCCESS.getMessage());
    }

    @DeleteMapping("/delete")
    public ApiResponse<?> delete(String shareId) {
        shareService.deleteShare(shareId);
        return ApiResponse.success(null, ResponseCode.FILE_LIST_SUCCESS.getMessage());
    }
}
