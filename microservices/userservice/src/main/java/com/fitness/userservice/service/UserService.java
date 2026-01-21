package com.fitness.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.mapper.UserMapper;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private UserRepository repository;
	private UserMapper userMapper;
	private PasswordEncoder passwordEncoder;

	public UserResponse register(RegisterRequest request) {

		// maps request to entity
		User user = userMapper.toEntity(request);

		// encrypt password before saving to DB
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		user.setPassword(encodedPassword);
		
		// saves to DB and gets populated object back
		User savedUser = repository.save(user);

		//maps the saved entity back to a response DTO
		return userMapper.toResponse(savedUser);
	}

}
