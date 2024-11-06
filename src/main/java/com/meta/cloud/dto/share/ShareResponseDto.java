package com.meta.cloud.dto.share;

import com.meta.cloud.domain.File;
import com.meta.cloud.domain.Share;
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
public class ShareResponseDto {
    private File file;
    private User ownUser;
    private User shareUser;
    private LocalDateTime shareAt;

    public ShareResponseDto toDto(Share share) {
        return ShareResponseDto.builder()
                .file(share.getFile())
                .ownUser(share.getOwnUser())
                .shareUser(share.getSharedUser())
                .build();
    }
}
