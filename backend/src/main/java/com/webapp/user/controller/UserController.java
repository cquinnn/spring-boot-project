package com.webapp.user.controller;

import com.webapp.common.dto.response.ApiResponse;
import com.webapp.user.dto.request.UserCreationRequest;
import com.webapp.user.dto.request.UserUpdateRequest;
import com.webapp.user.dto.response.UserResponse;
import com.webapp.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
  UserService userService;

  @PostMapping
  ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
    return ApiResponse.<UserResponse>builder().result(userService.createUser(request)).build();
  }

  @GetMapping
  ApiResponse<Page<UserResponse>> getUsers(
      @RequestParam(defaultValue = "0") int pageNo,
      @RequestParam(defaultValue = "10") int pageSize) {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Username: {}", authentication.getName());
    authentication
        .getAuthorities()
        .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
    return ApiResponse.<Page<UserResponse>>builder()
        .result(userService.getUsers(pageNo, pageSize))
        .build();
  }

  @GetMapping("/{userId}")
  ApiResponse<UserResponse> getUser(@PathVariable String userId) {
    return ApiResponse.<UserResponse>builder().result(userService.getUser(userId)).build();
  }

  @GetMapping("/myInfo")
  ApiResponse<UserResponse> getMyInfo() {
    return ApiResponse.<UserResponse>builder().result(userService.getMyInfo()).build();
  }

  @PutMapping("/{userId}")
  ApiResponse<UserResponse> updateUser(
      @PathVariable String userId, @RequestBody UserUpdateRequest request) {
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
