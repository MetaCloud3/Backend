package com.meta.cloud.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "login_pw", nullable = false)
    private String loginPw;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "total_storage", nullable = false)
    private Integer totalStorage;

    @Column(name = "used_storage", nullable = false)
    private Integer usedStorage;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    @Builder
    public Users(String loginId, String loginPw, String name, String email, Integer totalStorage, Integer usedStorage, String createdAt, String updatedAt) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.email = email;
        this.totalStorage = totalStorage;
        this.usedStorage = usedStorage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
