package com.example.chatweb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.chatweb.configuration.WebsocketConfiguration;
import com.example.chatweb.entity.FriendShip;
import com.example.chatweb.entity.Message;
import com.example.chatweb.entity.User;
import com.example.chatweb.payload.LoginRequest;
import com.example.chatweb.payload.MessageInput;
import com.example.chatweb.payload.MessageOutput;
import com.example.chatweb.payload.UserRequest;
import com.example.chatweb.payload.UserResponse;
import com.example.chatweb.repository.FriendShipRepository;
import com.example.chatweb.service.MessageService;
import com.example.chatweb.service.StorageService;
import com.example.chatweb.service.UserService;
import com.example.chatweb.service.impl.MessageServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
@CrossOrigin
public class SimpleCatBoxController {

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
	public MessageOutput welcomeToTeCat(MessageInput messageInput) {
		User userSend = userService.findByUserId(messageInput.getUserSendId()).get();
		User UserReceived = userService.findByUserId(messageInput.getUserReceivedId()).get();
		Message message = new Message(null, messageInput.getText(), new Date(), userSend, UserReceived);
		String time = new SimpleDateFormat("HH:mm").format(message.getTime());
		messageService.save(message);
		return new MessageOutput(messageInput.getText(),true,messageInput.getUserSendId(), messageInput.getUserReceivedId(), time);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginInfo) {
		User user = userService.checkLogin(loginInfo.getUsername(), loginInfo.getPassword());
		String lastActive = new SimpleDateFormat("HH:mm").format(user.getLastActive());
		if (user != null) {
			return ResponseEntity.ok().body(new UserResponse(user.getUserId(),user.getName(),user.getIcon(),user.getColor(),user.isOnline(),lastActive)); 		
					}
		return ResponseEntity.ok().body(null);

	}
	
	@GetMapping("/user/{userId}")
	public UserResponse getUserById(@PathVariable UUID userId) {
		User user = userService.findByUserId(userId).get();
		UserResponse userResponse = UserResponse.convertToUserResponse(user);
		return userResponse;
	}
	
	@PostMapping("/messages/send")
	public String postMessage(@RequestBody MessageInput messageInput) {
		User userSend = userService.findByUserId(messageInput.getUserSendId()).get();
		User UserReceived = userService.findByUserId(messageInput.getUserReceivedId()).get();
		Message message = new Message(null, messageInput.getText(), new Date(), userSend, UserReceived);
		messageService.save(message);
		return "Successfully";
	}

	@PostMapping("/users")
	public UserResponse postUser(@RequestParam("file") MultipartFile file,@RequestParam("user") String userRequest) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		UserRequest user = objectMapper.readValue(userRequest,UserRequest.class);
		User user1 = new User(user.getName(), user.getPassword(), file.getOriginalFilename(),"#f34ab2", true, new Date());
		
		storageService.store(file);
		UserResponse userResponse = UserResponse.convertToUserResponse(userService.store(user1));
		

		return userResponse;

	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.listAllUser();
	}

	@GetMapping("/users/friendship/{userId}")
	public List<UserResponse> listUser(@PathVariable UUID userId) {
		User user = userService.findByUserId(userId).get();

		List<FriendShip> listOfFriendShip = friendShipRepository.findAllFriendShipByUser(user);
		List<UserResponse> listUser = new ArrayList<UserResponse>();
		listUser.add(UserResponse.convertToUserResponse(user));
		for (FriendShip friendShip : listOfFriendShip) {
			User addedUser = friendShip.getUser1().getUserId() == user.getUserId() ? friendShip.getUser2()
					: friendShip.getUser1();
			listUser.add(UserResponse.convertToUserResponse(addedUser));
		}
		return listUser;
	}

	@GetMapping("/messages/{userId}/{friendId}")
	public List<MessageOutput> listMessage(@PathVariable UUID userId, @PathVariable UUID friendId) {

		List<MessageOutput> listMessagesOutput = messageService.listMessagesByUser(userId, friendId);
		return listMessagesOutput;
	}
}
