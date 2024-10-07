package com.webapp.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.webapp.identity.dto.request.UserCreationRequest;
import com.webapp.identity.dto.request.UserUpdateRequest;
import com.webapp.identity.dto.response.UserResponse;
import com.webapp.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mapping(target="roles", ignore=true)
	User toUser(UserCreationRequest request);
	UserResponse toUserResponse(User user);
	@Mapping(target="roles", ignore=true)
	void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
