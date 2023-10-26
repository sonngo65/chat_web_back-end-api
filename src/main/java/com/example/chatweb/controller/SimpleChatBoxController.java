package com.example.chatweb.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.chatweb.configuration.WebsocketConfiguration;
import com.example.chatweb.payload.MessageDto;
import com.example.chatweb.payload.MessageRequest;
import com.example.chatweb.service.MessageService;

@RestController
@RequestMapping("/api/v1")
@ResponseBody
@CrossOrigin
public class SimpleChatBoxController {

	@Autowired
	private MessageService messageService;

	public final String BASE_URL = WebsocketConfiguration.SIMPLE_CAT_BOX_URL;

	@MessageMapping(BASE_URL)
	@SendTo(BASE_URL)
	public ResponseEntity<MessageDto> welcomeToTheChat(MessageRequest messageRequest) {
		MessageDto messageDto = messageService.senMessage(messageRequest);
		return new ResponseEntity<>(messageDto, HttpStatus.OK);
	}

}
