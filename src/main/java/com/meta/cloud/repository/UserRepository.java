package com.meta.cloud.repository;

import com.meta.cloud.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
