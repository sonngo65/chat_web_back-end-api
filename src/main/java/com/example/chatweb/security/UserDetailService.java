package com.example.chatweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.chatweb.entity.User;
import com.example.chatweb.exception.ResourceNotFoundException;
import com.example.chatweb.repository.UserRepository;

@Component
public class UserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username).orElseThrow(()-> new ResourceNotFoundException("User", new String[] {"username"}, new Object[] {username}));
		return new UserDetailsCustom(user.getName(), user.getPassword(), user.getRoles());
	}

}
