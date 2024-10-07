package com.webapp.identity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webapp.identity.dto.request.UserCreationRequest;
import com.webapp.identity.dto.request.UserUpdateRequest;
import com.webapp.identity.dto.response.UserResponse;
import com.webapp.identity.entity.Role;
import com.webapp.identity.entity.User;
import com.webapp.identity.exception.AppException;
import com.webapp.identity.exception.ErrorCode;
import com.webapp.identity.mapper.UserMapper;
import com.webapp.identity.repository.RoleRepository;
import com.webapp.identity.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
	UserRepository userRepo;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepo;
	
	public UserResponse createUser(UserCreationRequest request) {
		// if (userRepo.existsByUsername(request.getUsername()))
		// 	throw new AppException(ErrorCode.USER_EXISTED);
		
		User user = userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		HashSet<Role> roles= new HashSet<>();
		request.getRoles().forEach(role -> roleRepo.findById(role).ifPresent(roles::add));
		user.setRoles(new HashSet<>(roles));
		try {
			user = userRepo.save(user);

		} catch (DataIntegrityViolationException exception){
			throw new AppException(ErrorCode.USER_EXISTED);
		}
		return userMapper.toUserResponse(user);
	}
	
	public UserResponse updateUser(String userId, UserUpdateRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		userMapper.updateUser(user, request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		var roles = roleRepo.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));
		return userMapper.toUserResponse(userRepo.save(user));
		
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getUsers() {
		return userRepo.findAll().stream().map(userMapper::toUserResponse).toList();
	}
	
	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse getUser(String userId) {
		return userMapper.toUserResponse(userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
	}

	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}
	
	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		User user = userRepo.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
		return userMapper.toUserResponse(user);
		
	}
	
}
