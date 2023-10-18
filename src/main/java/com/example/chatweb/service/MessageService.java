package com.example.chatweb.service;

import java.util.List;
import java.util.UUID;

import com.example.chatweb.entity.Message;
import com.example.chatweb.entity.User;
import com.example.chatweb.payload.MessageDto;
import com.example.chatweb.payload.MessageRequest;

public interface MessageService {
	public Message save(Message message);
	public List<MessageDto> listMessagesByUser(UUID userId,UUID friendId);
	public MessageDto senMessage(MessageRequest messageRequest);
}
