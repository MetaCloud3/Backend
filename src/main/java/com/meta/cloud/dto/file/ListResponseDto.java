package com.meta.cloud.dto.file;

import com.meta.cloud.domain.File;
import com.meta.cloud.domain.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResponseDto {
    private String id;
    private String uploadFileName;
    private FileType type;
    private Long size;
    private String userId;
    private LocalDateTime uploadedAt;

    public ListResponseDto toDto(File entity) {
        return ListResponseDto.builder()
                .id(entity.getId())
                .uploadFileName(entity.getUploadFileName())
                .type(entity.getType())
                .size(entity.getSize())
                .userId(entity.getUser().getLoginId())
                .uploadedAt(entity.getUploadedAt())
                .build();
    }
}
