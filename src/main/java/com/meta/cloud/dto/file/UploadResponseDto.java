package com.meta.cloud.dto.file;

import com.meta.cloud.domain.File;
import com.meta.cloud.domain.FileType;
import com.meta.cloud.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDto {
    private String id;
    private String uploadFileName;
    private String storeFileName;
    private FileType type;
    private Long size;
    private String path;
    private User user;
    private Integer downloaded;
    private LocalDateTime uploadedAt;

    public UploadResponseDto toDto(File entity) {
        return UploadResponseDto.builder()
                .id(entity.getId())
                .uploadFileName(entity.getUploadFileName())
                .storeFileName(entity.getStoreFileName())
                .type(entity.getType())
                .size(entity.getSize())
                .path(entity.getPath())
                .user(entity.getUser())
                .downloaded(entity.getDownloaded())
                .uploadedAt(entity.getUploadedAt())
                .build();
    }
}
