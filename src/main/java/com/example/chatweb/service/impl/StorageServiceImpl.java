package com.example.chatweb.service.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.chatweb.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {
	
	@Autowired
	private Path location;
	
	@Override
	public void store(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try(InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream,location.resolve(fileName),StandardCopyOption.REPLACE_EXISTING );
		}catch(Exception e) {

			System.out.print("file storage exception : " + e);
		}
	}

	@Override
	public boolean checkGetImageException(String fileName) {

		var imageFile = new ClassPathResource("com/example/chatweb/image/" + fileName);
		InputStreamResource resource = null;
		try {
			resource  = new InputStreamResource(imageFile.getInputStream());
		}catch(FileNotFoundException e) {
			return false;
		}catch (Exception e) {
			return true;
		}
		
		return true;
	}
	

}
