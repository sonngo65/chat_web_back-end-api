package com.example.chatweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.chatweb.entity.FriendShip;
import com.example.chatweb.entity.Role;
import com.example.chatweb.entity.User;
import com.example.chatweb.exception.ResourceNotFoundException;
import com.example.chatweb.payload.UserDto;
import com.example.chatweb.payload.UserRequest;
import com.example.chatweb.repository.FriendShipRepository;
import com.example.chatweb.repository.RoleRepository;
import com.example.chatweb.repository.UserRepository;
import com.example.chatweb.service.StorageService;
import com.example.chatweb.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;




@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private FriendShipRepository friendShipRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public User store(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	@Override
	public UserDto checkLogin(String username, String password)  {
		String[] fieldsName = {"username","password"};
		Object[] fieldsValue = {username,passwordEncoder.encode(password)};
		User user = userRepository.findByNameAndPassword(username,passwordEncoder.encode(password)).orElseThrow(()-> new ResourceNotFoundException("User",fieldsName,fieldsValue));
		String lastActive = new SimpleDateFormat("HH:mm").format(user.getLastActive());
		return new UserDto(user.getUserId(), user.getName(), user.getIcon(),
				user.getColor(), user.isOnline(), lastActive);
	}
	
	@Override
	public UserDto findByUserId(UUID id) {
		String[] fieldsName = {"id"};
		Object[] fieldsValue = {id};
		User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", fieldsName, fieldsValue));
		UserDto userDto = UserDto.convertToUserResponse(user);

		return userDto;
	}
	@Override
	public List<UserDto> listAllUser() {
		List<User> listUser = userRepository.findAll();	
		return listUser.stream().map((user)-> UserDto.convertToUserResponse(user)).collect(Collectors.toList());
	}
	@Override
	public List<UserDto> listAllFriendOfUserById(UUID userId) {
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", new String[] {"id"}, new Object[] {userId}));

		List<FriendShip> listOfFriendShip = friendShipRepository.findAllFriendShipByUser(user);
		List<UserDto> listUser = new ArrayList<UserDto>();
		listUser.add(UserDto.convertToUserResponse(user));
		for (FriendShip friendShip : listOfFriendShip) {
			User addedUser = friendShip.getUser1().getUserId() == user.getUserId() ? friendShip.getUser2()
					: friendShip.getUser1();
			listUser.add(UserDto.convertToUserResponse(addedUser));
		}
		return listUser;
	}
	@Override
	public UserDto createUser(MultipartFile file, UserRequest userRequest) {
		User user = new User(userRequest.getName(), passwordEncoder.encode(userRequest.getPassword()), file.getOriginalFilename(), "#f34ab2", true,
				new Date());
		Role newRole = new Role("USER");
		Role savedRole = roleRepository.save(newRole);
		Set<Role> roles  = new HashSet<>();
		
		roles.add(savedRole);
		
		user.setRoles(roles);
		
		User savedUser = userRepository.save(user);
		
//		roleRepository.save(savedRole);
		
		storageService.store(file);
		
		UserDto userResponse = UserDto.convertToUserResponse(savedUser);
		return userResponse;
	}

}
