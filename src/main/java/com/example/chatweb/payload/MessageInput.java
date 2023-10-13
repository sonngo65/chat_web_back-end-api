package com.example.chatweb.payload;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInput {
	private String text;
	private UUID userSendId;
	private UUID userReceivedId;
}
