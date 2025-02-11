package com.webapp.user.mapper;

import com.webapp.user.dto.request.UserCreationRequest;
import com.webapp.user.dto.request.UserUpdateRequest;
import com.webapp.user.dto.response.UserResponse;
import com.webapp.auth.mapper.RoleMapper;
import com.webapp.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
  User toUser(UserCreationRequest request);

  User toUser(UserResponse response);

  UserResponse toUserResponse(User user);


  void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
