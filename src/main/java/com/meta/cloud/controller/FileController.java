package com.meta.cloud.controller;

import com.meta.cloud.domain.File;
import com.meta.cloud.dto.file.ListResponseDto;
import com.meta.cloud.dto.file.UploadResponseDto;
import com.meta.cloud.service.FileService;
import com.meta.cloud.util.api.ApiResponse;
import com.meta.cloud.util.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ApiResponse<UploadResponseDto> upload(@RequestParam("file") MultipartFile file, Authentication authentication) {
        return ApiResponse.success(fileService.upload(file, authentication.getName()), ResponseCode.FILE_UPLOAD_SUCCESS.getMessage());
    }

//    @PostMapping("/download")
//    public

    @GetMapping("/list")
    public ApiResponse<List<ListResponseDto>> list(Authentication authentication) {
        return ApiResponse.success(fileService.findByUserId(authentication.getName()), ResponseCode.FILE_LIST_SUCCESS.getMessage());
    }

}
