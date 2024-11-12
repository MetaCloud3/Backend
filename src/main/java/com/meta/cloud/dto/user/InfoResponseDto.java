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
public class InfoResponseDto {

    private String loginId;
    private Integer usedStorage;


    public InfoResponseDto toDto(User entity) {
        return InfoResponseDto.builder()
                .loginId(entity.getLoginId())
                .usedStorage(entity.getUsedStorage())
                .build();
    }
}
