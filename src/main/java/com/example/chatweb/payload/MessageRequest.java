package com.example.chatweb.payload;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
	
	@NotEmpty(message =  "text should not empty")
	private String text;
	
	@NotNull
	private UUID userSendId;
	
	@NotNull
	private UUID userReceivedId;
}
