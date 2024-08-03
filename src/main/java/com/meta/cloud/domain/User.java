package com.meta.cloud.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "tb_user")
public class User {
    @Id
    private String id;
    private String loginId;
    private String loginPw;
    private String name;
    private String email;
    private Integer totalStorage;
    private Integer usedStorage;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public User(String loginId, String loginPw, String name, String email) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.email = email;
        this.totalStorage = 0;
        this.usedStorage = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void setId() {
        this.id = "USER_" + UUID.randomUUID().toString().substring(0, 16);
    }

}
