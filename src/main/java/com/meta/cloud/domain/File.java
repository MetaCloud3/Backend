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
    private String name;
    @Enumerated(EnumType.STRING)
    private FileType type;
    private Long size;
    private String path;
    private Integer downloaded;
    @CreatedDate
    private LocalDateTime uploadedAt;

    @Builder
    public File(String name, FileType type, Long size, String path) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.path = path;
        this.downloaded = 0;
        this.uploadedAt = LocalDateTime.now();
    }

    @PrePersist
    public void setId() {
        this.id = "FILE_" + UUID.randomUUID().toString().substring(0, 16);
    }
}
