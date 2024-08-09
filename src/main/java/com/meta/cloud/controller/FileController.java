package com.meta.cloud.controller;

import com.meta.cloud.dto.file.UploadResponseDto;
import com.meta.cloud.service.FileService;
import com.meta.cloud.util.api.ApiResponse;
import com.meta.cloud.util.api.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ApiResponse<UploadResponseDto> upload(@RequestParam("file") MultipartFile file) {
        return ApiResponse.success(fileService.upload(file), ResponseCode.USER_CREATE_SUCCESS.getMessage());
    }
}
