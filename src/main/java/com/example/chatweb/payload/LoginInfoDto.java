package com.example.chatweb.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDto {
	@Schema(name = "username",example = "sonngo3",requiredMode = RequiredMode.REQUIRED)
	private String username;
	@Schema(name= "password", example = "sonkk11" , requiredMode = RequiredMode.REQUIRED)
	private String password;
}
