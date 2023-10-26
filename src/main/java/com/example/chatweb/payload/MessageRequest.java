package com.example.chatweb.payload;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(name = "text", example = "hello nha")
	private String text;
	
	@NotNull
	@Schema(name = "userSendId", example = "014a4d04-ebbb-4238-b0a3-0b2a752ee5c1")
	private UUID userSendId;
	
	@NotNull
	@Schema(name = "userReceivedId", example = "7e45c99c-6c0b-42f4-819e-821c2f81d386")
	private UUID userReceivedId;
}
