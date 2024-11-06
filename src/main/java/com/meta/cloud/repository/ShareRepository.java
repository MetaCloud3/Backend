package com.meta.cloud.repository;

import com.meta.cloud.domain.Share;
import com.meta.cloud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, String> {
    List<Share> findBySharedUser(User shared);
    List<Share> findByOwnUser(User own);
}
