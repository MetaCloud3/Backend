package com.meta.cloud.repository;

import com.meta.cloud.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
