package com.webapp.module.user.mapper;

import com.webapp.model.User;
import com.webapp.module.user.dto.request.UpdateUserRequest;
import com.webapp.module.user.dto.request.UserCreationRequest;
import com.webapp.module.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    User toUser(UserResponse response);

    UserResponse toUserResponse(User user);


    void updateUser(@MappingTarget User user, UpdateUserRequest request);
}
