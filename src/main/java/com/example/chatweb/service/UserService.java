package com.example.chatweb.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.chatweb.entity.User;
import com.example.chatweb.payload.UserDto;
import com.example.chatweb.payload.UserRequest;

public interface UserService {
	public User store(User user);
	public UserDto checkLogin(String username,String password);
	UserDto findByUserId(UUID id);
	public List<UserDto> listAllUser();
	List<UserDto> listAllFriendOfUserById(UUID userId);
	UserDto createUser(MultipartFile file,UserRequest userRequest);
}
