package com.meta.cloud.dto.user;

import com.meta.cloud.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String loginId;
    private String loginPw;
    private String name;
    private String email;
    private Integer totalStorage;
    private Integer usedStorage;
    private String createdAt;
    private String updatedAt;

    public UserResponseDto toDto(User entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .loginId(entity.getLoginId())
                .loginPw(entity.getLoginPw())
                .name(entity.getName())
                .email(entity.getEmail())
                .totalStorage(entity.getTotalStorage())
                .usedStorage(entity.getUsedStorage())
                .createdAt(entity.getCreatedAt().toString())
                .updatedAt(entity.getUpdatedAt().toString())
                .build();
    }
}
