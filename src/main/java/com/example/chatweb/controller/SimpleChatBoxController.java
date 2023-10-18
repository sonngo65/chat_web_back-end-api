package com.example.chatweb.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import com.example.chatweb.configuration.WebsocketConfiguration;
import com.example.chatweb.entity.FriendShip;
import com.example.chatweb.entity.Message;
import com.example.chatweb.entity.User;
import com.example.chatweb.payload.LoginInfoDto;
import com.example.chatweb.payload.MessageDto;
import com.example.chatweb.payload.MessageRequest;
import com.example.chatweb.payload.UserDto;
import com.example.chatweb.payload.UserRequest;
import com.example.chatweb.repository.FriendShipRepository;
import com.example.chatweb.service.MessageService;
import com.example.chatweb.service.StorageService;
import com.example.chatweb.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@ResponseBody
@CrossOrigin
public class SimpleChatBoxController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private FriendShipRepository friendShipRepository;

	@Autowired
	private UserService userService;

	public final String BASE_URL = WebsocketConfiguration.SIMPLE_CAT_BOX_URL;

	@MessageMapping(BASE_URL)
	@SendTo(BASE_URL)
	public ResponseEntity<MessageDto> welcomeToTheChat(MessageRequest messageRequest) {
		MessageDto messageDto = messageService.senMessage(messageRequest);
		return new ResponseEntity<>(messageDto,HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginInfoDto loginInfo) {
		UserDto userResponse = userService.checkLogin(loginInfo.getUsername(), loginInfo.getPassword());
		return ResponseEntity.ok().body(userResponse);

	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
		UserDto userDto = userService.findByUserId(userId);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}

	@PostMapping("/messages")
	public ResponseEntity<MessageDto> postMessage(@Valid @RequestBody MessageRequest messageRequest) throws Exception {
		MessageDto messageDto = messageService.senMessage(messageRequest);
		return new ResponseEntity<>(messageDto,HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<UserDto> postUser(@RequestParam("file") MultipartFile file, @RequestParam("user") String userRequest)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		UserRequest user = objectMapper.readValue(userRequest, UserRequest.class);
		
		return new ResponseEntity<>(userService.createUser(file, user),HttpStatus.OK);

	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getUsers() throws Exception {
		
		return new ResponseEntity<>(userService.listAllUser(),HttpStatus.OK);
	}

	@GetMapping("/users/{userId}/friendship")
	public ResponseEntity<List<UserDto>> listUser(@PathVariable UUID userId) {
		
		return new ResponseEntity<>(userService.listAllFriendOfUserById(userId),HttpStatus.OK);
	}

	@GetMapping("/messages/{userId}/friendship/{friendId}")
	public ResponseEntity<List<MessageDto>> listMessage(@PathVariable UUID userId, @PathVariable UUID friendId) {

		List<MessageDto> listMessagesOutput = messageService.listMessagesByUser(userId, friendId);
		return new ResponseEntity<>(listMessagesOutput,HttpStatus.OK);
	}
}
