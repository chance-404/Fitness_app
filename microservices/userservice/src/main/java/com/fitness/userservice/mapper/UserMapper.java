package com.fitness.userservice.mapper;

import org.mapstruct.Mapper;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	// converts request DTO to database entity
	User toEntity(RegisterRequest request);
	
	// converts database entity to response DTO
	UserResponse toResponse(User user);
}
