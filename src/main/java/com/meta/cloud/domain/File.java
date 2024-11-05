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
@Table(name = "tb_file")
public class File {
    @Id
    private String id;
    private String uploadFileName;
    private String storeFileName;
    @Enumerated(EnumType.STRING)
    private FileType type;
    private Long size;
    private String path;
    private Integer downloaded;
    @CreatedDate
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public File(String uploadFileName, String storeFileName, FileType type, Long size, String path, User user) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.type = type;
        this.size = size;
        this.path = path;
        this.user = user;
        this.downloaded = 0;
        this.uploadedAt = LocalDateTime.now();
    }

    @PrePersist
    public void setId() {
        this.id = "FILE_" + UUID.randomUUID().toString().substring(0, 16);
    }
}
