package com.meta.cloud.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "tb_share")
public class Share {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "own_id")
    private User ownUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_id")
    private User sharedUser;

    @CreatedDate
    private LocalDateTime sharedAt;

    @Builder
    public Share(File file, User ownUser, User sharedUser) {
        this.file = file;
        this.ownUser = ownUser;
        this.sharedUser = sharedUser;
        this.sharedAt = LocalDateTime.now();
    }

    @PrePersist
    public void setId() {
        this.id = "SHARE_" + UUID.randomUUID().toString().substring(0, 16);
    }
}
