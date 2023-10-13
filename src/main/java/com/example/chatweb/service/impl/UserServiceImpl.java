package com.example.chatweb.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.chatweb.entity.User;
import com.example.chatweb.repository.UserRepository;
import com.example.chatweb.service.StorageService;
import com.example.chatweb.service.UserService;




@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StorageService storageService;
	
	@Override
	public User store(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	@Override
	public User checkLogin(String username, String password) {
		User user = userRepository.findByNameAndPassword(username, password);

		return user;
	}
	@Override
	public Optional<User> findByUserId(UUID id) {
		Optional<User> user = userRepository.findById(id);
		return user;
	}
	@Override
	public List<User> listAllUser() {
		List<User> listUser = userRepository.findAll();
		for (User user : listUser) {
			user.setIcon(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(user.getIcon()).toUriString());
		}
		return listUser;
	}

}
