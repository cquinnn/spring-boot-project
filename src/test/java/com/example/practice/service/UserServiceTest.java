package com.example.practice.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.entity.Role;
import com.example.practice.entity.User;
import com.example.practice.mapper.UserMapper;
import com.example.practice.repository.RoleRepository;
import com.example.practice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
	@InjectMocks
	private UserService userService;
	
	@MockBean
	private UserMapper userMapper;
	private UserRepository userRepo;
	private UserCreationRequest request;
	private UserResponse userResponse;
	private LocalDate dob;
	private Set<Role> roles;
	private User user;
	
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepo;
	
	@BeforeEach
	void initData() {
		roles = Set.of(Role.builder().name("USER").build());
		dob = LocalDate.of(2001, 10, 10);
		request = UserCreationRequest.builder()
				.username("test")
				.firstName("Quynh")
				.lastName("Vo")
				.password("12345678")
				.dob(dob)
				.build();
		
		userResponse = UserResponse.builder()
				.id("asb408cf23")
				.username("test").
				firstName("Quynh")
				.lastName("Vo")
				.dob(dob)
				.build();
		
		user = User.builder()
				.username("test")
				.firstName("Quynh").lastName("Vo")
				.password("12345678")
				.dob(dob)
				.roles(roles)
				.build();
		
	}
	
	@Test
	void createValidUser() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		//given
		when(userRepo.existsByUsername(request.getUsername())).thenReturn(false);
		when(userMapper.toUser(request)).thenReturn(user);
		when(passwordEncoder.encode(request.getPassword())).thenReturn("12345678");
		when(roleRepo.findAllById(request.getRoles())).thenReturn((List<Role>) roles);
		when(userMapper.toUserResponse(user)).thenReturn(userResponse);
		// WHEN
		
		assertTrue(userResponse==userService.createUser(request));
	}
}
