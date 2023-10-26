package com.example.chatweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatweb.payload.JwtAuthResponse;
import com.example.chatweb.payload.LoginInfoDto;
import com.example.chatweb.payload.UserDto;
import com.example.chatweb.security.JwtUtils;
import com.example.chatweb.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/JwtAuth/{username}")
	public ResponseEntity<JwtAuthResponse> generateAccessToken(@PathVariable String username){
		String accessToken = jwtUtils.generateJwtToken(username);
				
		return new ResponseEntity<>(new JwtAuthResponse(accessToken,"Bearer"),HttpStatus.OK);
	}
	
	@Operation(summary = "login", description = "login by username and password")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login Successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthResponse.class))
					)
	})
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginInfoDto loginInfo) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword()));
		if(authentication.isAuthenticated()) {
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return ResponseEntity.ok().body(new JwtAuthResponse(jwtUtils.generateJwtToken(authentication.getName()),"Bearer" ));

		}
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);

	}

}
