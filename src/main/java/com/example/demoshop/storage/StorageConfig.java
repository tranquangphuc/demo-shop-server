package com.example.demoshop.storage;

import java.nio.file.Paths;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfig implements WebMvcConfigurer {

	@Autowired
	StorageProperties storageProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String storageLocation = Paths.get(storageProperties.getLocation()).toAbsolutePath().toUri().toString();
		log.info("storage location: {}", storageLocation);
		registry.addResourceHandler("/files/**").addResourceLocations(storageLocation)
				.setCacheControl(CacheControl.maxAge(Duration.ofHours(1)));
		;
	}
}
