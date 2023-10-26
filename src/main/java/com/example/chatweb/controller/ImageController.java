package com.example.chatweb.controller;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.chatweb.service.StorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Controller
@CrossOrigin
@RequestMapping("/api/v1")
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
	
	
	@Operation(summary = "Get Image", description= "Get Image By Filename API REST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description= "Get Image Successfully", content = @Content(mediaType = "image", schema = @Schema(implementation  = InputStreamResource.class)))
	})
	@SecurityRequirement(name = "Authorization Bearer")
	@ResponseBody
	@GetMapping("image/{filename}")
	public ResponseEntity<InputStreamResource> getImage(@PathVariable @Parameter(name = "filename",example = "Thoi-trang-nam.jpg") String filename) throws IOException, InterruptedException {
		while(!storageService.checkGetImageException(filename)) {
			Thread.sleep(200);
		}
		
		var imageFile = new ClassPathResource("com/example/chatweb/image/" + filename);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(imageFile.getInputStream()));
	}
}
