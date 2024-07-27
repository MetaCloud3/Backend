package com.meta.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.meta.cloud.repository")
@EntityScan(basePackages = "com.meta.cloud.entity")
public class MetaCloudApplication {
	public static void main(String[] args) {
		SpringApplication.run(MetaCloudApplication.class, args);
	}
}
