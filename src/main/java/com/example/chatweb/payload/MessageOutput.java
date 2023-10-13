package com.example.chatweb.payload;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageOutput {
	private String text;
	private boolean output;
	private UUID userSendId;
	private UUID userReceivedId;
	private String time;
}
