package com.example.chatweb.controller;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatweb.payload.MessageDto;
import com.example.chatweb.payload.MessageRequest;
import com.example.chatweb.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	
	@Operation(summary = "Post Message", description = "Post Message API REST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description= "Post Message Successfully", 
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = MessageDto.class)))
	})
	@SecurityRequirement(name = "Authorization Bearer")
	@PostMapping("/messages")
	public ResponseEntity<MessageDto> postMessage(@Valid @RequestBody MessageRequest messageRequest) throws Exception {
		MessageDto messageDto = messageService.senMessage(messageRequest);
		return new ResponseEntity<>(messageDto,HttpStatus.OK);
	}
	
	@Operation(summary = "Get Messages", description = "Get Messages By User Id and Friend Id API REST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",description= "Get Messages Successfully")
	})
	@SecurityRequirement(name = "Authorization Bearer")
	@GetMapping("/messages/{userId}/friendship/{friendId}")
	public ResponseEntity<List<MessageDto>> listMessage(@PathVariable @Parameter(name = "userId", example = "014a4d04-ebbb-4238-b0a3-0b2a752ee5c1") UUID userId, @PathVariable @Parameter(name = "friendId", example = "7e45c99c-6c0b-42f4-819e-821c2f81d386") UUID friendId) {

		List<MessageDto> listMessagesOutput = messageService.listMessagesByUser(userId, friendId);
		return new ResponseEntity<>(listMessagesOutput,HttpStatus.OK);
	}
}
