package com.meta.cloud.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    private String loginId;
    private String loginPw;
    private String name;
    private String email;
    private Integer totalStorage;
    private Integer UsedStorage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
