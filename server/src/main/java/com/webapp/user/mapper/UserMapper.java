package com.webapp.user.mapper;

import com.webapp.user.dto.request.UserCreationRequest;
import com.webapp.user.dto.request.UserUpdateRequest;
import com.webapp.user.dto.response.UserResponse;
import com.webapp.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(target = "roles", ignore = true)
  User toUser(UserCreationRequest request);

  UserResponse toUserResponse(User user);

  @Mapping(target = "roles", ignore = true)
  void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
