package com.example.practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.request.UserUpdateRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mapping(target="roles", ignore=true)
	User toUser(UserCreationRequest request);
	UserResponse toUserResponse(User user);
	@Mapping(target="roles", ignore=true)
	void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
