package com.meta.cloud.repository;

import com.meta.cloud.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, String> {
    List<File> findByUser_Id(String userId);
}
