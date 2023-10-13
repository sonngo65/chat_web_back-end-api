package com.example.chatweb.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.chatweb.entity.User;

public interface UserService {
	public User store(User user);
	public User checkLogin(String username,String password);
	 Optional<User> findByUserId(UUID id);
	public List<User> listAllUser();
}
