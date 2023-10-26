package com.example.chatweb.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JwtAuthResponse {
	private String token;
	private String tokenType;
}
