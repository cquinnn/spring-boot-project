package com.webapp.module.user.service;

import com.webapp.model.Role;
import com.webapp.module.auth.repository.RoleRepository;
import com.webapp.common.exception.AppException;
import com.webapp.common.exception.ErrorCode;
import com.webapp.module.user.dto.request.UserCreationRequest;
import com.webapp.module.user.dto.request.UserUpdateRequest;
import com.webapp.module.user.dto.response.UserResponse;
import com.webapp.module.user.entity.User;
import com.webapp.module.user.mapper.UserMapper;
import com.webapp.module.user.repository.UserRepository;

import java.util.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
  UserRepository userRepo;
  UserMapper userMapper;
  PasswordEncoder passwordEncoder;
  RoleRepository roleRepo;

  @Transactional
  public UserResponse createUser(UserCreationRequest request) {
    if (userRepo.existsByUsername(request.getUsername()))
     	throw new AppException(ErrorCode.USER_EXISTED);

    User user = userMapper.toUser(request);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Role role = roleRepo.findByName("USER").orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    user.setRoles(Collections.singletonList(role));
    try {
      return userMapper.toUserResponse(userRepo.save(user));
    } catch (DataIntegrityViolationException exception) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

  }

  @Transactional
  public synchronized UserResponse updateUser(String userId, UserUpdateRequest request) {
    User user = userRepo.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    userMapper.updateUser(user, request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    return userMapper.toUserResponse(userRepo.save(user));
  }

  @PreAuthorize("hasRole('ADMIN')")
  public Page<UserResponse> getUsers(int pageNo, int pageSize) {
    Page<User> users = userRepo.findAll(PageRequest.of(pageNo, pageSize));
      return users.map(userMapper::toUserResponse);
  }

  @PostAuthorize("returnObject.username == authentication.name")
  public UserResponse getUser(String userId) {
    return userMapper.toUserResponse(
        userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
  }

  public synchronized void deleteUser(String userId) {
    userRepo.deleteById(userId);
  }

  public UserResponse getMyInfo() {
    var context = SecurityContextHolder.getContext();
    String name = context.getAuthentication().getName();
    User user =
        userRepo.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
    return userMapper.toUserResponse(user);
  }
}
