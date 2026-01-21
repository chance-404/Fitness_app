package com.fitness.userservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.exceptions.EmailAlreadyExistsException;
import com.fitness.userservice.exceptions.UserNotFoundException;
import com.fitness.userservice.mapper.UserMapper;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
	private final UserRepository repository;
	private final UserMapper userMapper;


	public UserService(UserRepository repository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponse register(RegisterRequest request) {

		if (repository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

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

	
	public UserResponse getUserProfile(String userId) {
		User user = repository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found"));

		return userMapper.toResponse(user);
	}

}