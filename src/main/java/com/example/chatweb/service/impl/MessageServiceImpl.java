package com.example.chatweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatweb.entity.Message;
import com.example.chatweb.entity.User;
import com.example.chatweb.exception.ResourceNotFoundException;
import com.example.chatweb.payload.MessageDto;
import com.example.chatweb.payload.MessageRequest;
import com.example.chatweb.repository.MessageRepository;
import com.example.chatweb.repository.UserRepository;
import com.example.chatweb.service.MessageService;
import com.example.chatweb.service.UserService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Comparator<Message> comparator;
	@Override
	public Message save(Message message) {

		return messageRepository.save(message);
	}

	@Override
	public List<MessageDto> listMessagesByUser(UUID userId, UUID friendId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User",new String[] {"id"},new Object[] {userId}));
		User friend = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User",new String[] {"id"},new Object[] {friendId}));
		List<Message> messages = messageRepository.listMessagesByUser(user,friend);
		messages.sort(comparator);
		List<MessageDto> listMessageOutput = new ArrayList<MessageDto>();
		for (Message message : messages) {
				String time = new SimpleDateFormat("HH:mm").format(message.getTime());

				listMessageOutput.add(new MessageDto(message.getText(), true,message.getUserSend().getUserId(),message.getUserReceived().getUserId(), time));
			
		}

		return listMessageOutput;
	}

	@Override
	public MessageDto senMessage(MessageRequest messageRequest) {
		User userSend = userRepository.findById(messageRequest.getUserSendId()).orElseThrow(()-> new ResourceNotFoundException("user", new String[] {"id"}, new Object[] {messageRequest.getUserSendId()}));
		User UserReceived = userRepository.findById(messageRequest.getUserSendId()).orElseThrow(()-> new ResourceNotFoundException("user", new String[] {"id"}, new Object[] {messageRequest.getUserReceivedId()}));
		Message message = new Message(null, messageRequest.getText(), new Date(), userSend, UserReceived);
		String time = new SimpleDateFormat("HH:mm").format(message.getTime());
		messageRepository.save(message);
		return new MessageDto(messageRequest.getText(), true, messageRequest.getUserSendId(),
				messageRequest.getUserReceivedId(), time);
	}

}
