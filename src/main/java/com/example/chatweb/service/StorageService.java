package com.example.chatweb.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	public void store(MultipartFile file);
	public boolean checkGetImageException(String fileName);
}
