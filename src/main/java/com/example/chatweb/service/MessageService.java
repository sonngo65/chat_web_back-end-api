package com.example.chatweb.service;

import java.util.List;
import java.util.UUID;

import com.example.chatweb.entity.Message;
import com.example.chatweb.entity.User;
import com.example.chatweb.payload.MessageOutput;

public interface MessageService {
	public Message save(Message message);
	public List<MessageOutput> listMessagesByUser(UUID userId,UUID friendId);;
}
