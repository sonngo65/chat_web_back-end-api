package com.example.chatweb.controller;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.chatweb.service.StorageService;

@Controller
public class ImageController {

	@Autowired
	private StorageService storageService;
	@Autowired
	private Path location;
	
	@PostMapping("image")
	public String uploadFile(@RequestParam MultipartFile file) {
		
		storageService.store(file);
		return "successfully";
	}
	
	@GetMapping("image/{filename}")
	public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) throws IOException, InterruptedException {
		while(!storageService.checkGetImageException(filename)) {
			Thread.sleep(200);
		}
		
		var imageFile = new ClassPathResource("com/example/chatweb/image/" + filename);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(imageFile.getInputStream()));
	}
}
