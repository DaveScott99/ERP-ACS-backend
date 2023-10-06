package com.astro.erpAcs.services;

import org.springframework.stereotype.Service;

import com.astro.erpAcs.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	
}
