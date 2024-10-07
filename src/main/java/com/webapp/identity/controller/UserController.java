package com.webapp.identity.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.identity.dto.request.ApiResponse;
import com.webapp.identity.dto.request.UserCreationRequest;
import com.webapp.identity.dto.request.UserUpdateRequest;
import com.webapp.identity.dto.response.UserResponse;
import com.webapp.identity.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
	UserService userService;
	
	@PostMapping
	ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.createUser(request))
				.build();
	}
	@GetMapping
	ApiResponse<List<UserResponse>> getUsers(){
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Username: {}", authentication.getName());
		authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
		return ApiResponse.<List<UserResponse>>builder()
				.result(userService.getUsers())
				.build();
	}
	@GetMapping("/{userId}")
	ApiResponse<UserResponse> getUser(@PathVariable String userId) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getUser(userId))
				.build();
	} 
	
	@GetMapping("/myInfo")
	ApiResponse<UserResponse> getMyInfo() {
		return ApiResponse.<UserResponse>builder()
				.result(userService.getMyInfo())
				.build();
	}
	
	@PutMapping("/{userId}")
	ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
		return ApiResponse.<UserResponse>builder()
				.result(userService.updateUser(userId, request))
				.build();
	}
	@DeleteMapping("/{userId}")
	ApiResponse<String> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return ApiResponse.<String>builder().result("User has been deleted").build();
	}
}
