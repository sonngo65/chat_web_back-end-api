package com.example.chatweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatweb.entity.Message;
import com.example.chatweb.entity.User;
import com.example.chatweb.payload.MessageOutput;
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
	public List<MessageOutput> listMessagesByUser(UUID userId, UUID friendId) {
		User user = userRepository.findById(userId).get();
		User friend = userRepository.findById(friendId).get();
		List<Message> messages = messageRepository.listMessagesByUser(user,friend);
		messages.sort(comparator);
		List<MessageOutput> listMessageOutput = new ArrayList<MessageOutput>();
		for (Message message : messages) {
				String time = new SimpleDateFormat("HH:mm").format(message.getTime());

				listMessageOutput.add(new MessageOutput(message.getText(), true,message.getUserSend().getUserId(),message.getUserReceived().getUserId(), time));
			
		}

		return listMessageOutput;
	}

}
