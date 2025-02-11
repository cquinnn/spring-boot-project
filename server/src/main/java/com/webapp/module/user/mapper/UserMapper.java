package com.webapp.module.user.mapper;

import com.webapp.module.user.dto.request.UserCreationRequest;
import com.webapp.module.user.dto.request.UserUpdateRequest;
import com.webapp.module.user.dto.response.UserResponse;
import com.webapp.module.auth.mapper.RoleMapper;
import com.webapp.module.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
  User toUser(UserCreationRequest request);

  User toUser(UserResponse response);

  UserResponse toUserResponse(User user);


  void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
