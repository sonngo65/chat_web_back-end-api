package com.example.chatweb.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.chatweb.payload.ErrorDetails;
import com.example.chatweb.payload.JwtAuthResponse;
import com.example.chatweb.payload.UserDto;
import com.example.chatweb.payload.UserRequest;
import com.example.chatweb.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	private final static String userExample = "{\"name\" : \"sonkk2002\", \"password\" : \"232313\"}";

	@Operation(summary = "gets all users ", description = "users must exists", security = @SecurityRequirement(name = "Authorization Bearer"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "401", description = "not security", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "200", description = "Get All Users") })
	@GetMapping(value = "/users", produces = { "application/json" })

	public ResponseEntity<List<UserDto>> getUsers() {
		return new ResponseEntity<>(userService.listAllUser(), HttpStatus.OK);
	}

	
	@Operation(summary = "Post User", description = "Post users API REST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Post User Successfully",content = @Content(mediaType = "application/json")) })
	@SecurityRequirement(name = "Authorization Bearer")
	@PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UserDto> postUser(@RequestParam("file") MultipartFile file,
			@Parameter(name = "user", description = "Additional request data", example = userExample) @RequestParam("user") String userRequest)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		UserRequest user = objectMapper.readValue(userRequest, UserRequest.class);

		return new ResponseEntity<>(userService.createUser(file, user), HttpStatus.OK);

	}

	@Operation(summary = "Get User By Id", description = "GEt User By Id API REST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Get User Successfully",content = @Content(mediaType = "application/json")) })
	@SecurityRequirement(name = "Authorization Bearer")
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getUserById(
			@PathVariable @Parameter(name = "userId", example = "014a4d04-ebbb-4238-b0a3-0b2a752ee5c1") UUID userId) {
		UserDto userDto = userService.findByUserId(userId);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	
	@Operation(summary = "Get User By Id", description = "GEt User By Id API REST")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Get User Successfully") })
	@SecurityRequirement(name = "Authorization Bearer")
	@GetMapping("/users/{userId}/friendship")
	public ResponseEntity<List<UserDto>> listUser(
			@PathVariable @Parameter(name = "userId", example = "014a4d04-ebbb-4238-b0a3-0b2a752ee5c1") UUID userId) {

		return new ResponseEntity<>(userService.listAllFriendOfUserById(userId), HttpStatus.OK);
	}

}
