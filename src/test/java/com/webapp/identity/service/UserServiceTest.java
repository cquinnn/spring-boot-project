package com.webapp.identity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.webapp.identity.dto.request.UserCreationRequest;
import com.webapp.identity.dto.response.UserResponse;
import com.webapp.identity.entity.User;
import com.webapp.identity.mapper.UserMapper;
import com.webapp.identity.repository.RoleRepository;
import com.webapp.identity.repository.UserRepository;
import com.webapp.identity.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;
    
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private RoleRepository roleRepo;
	@MockBean
	private UserMapper userMapper;
	private UserCreationRequest request;
	private UserResponse userResponse;
	private LocalDate dob;
	private User user;
	


	@BeforeEach
	void initData() {
		dob = LocalDate.of(2001, 10, 10);
		request = UserCreationRequest.builder().username("test").firstName("Quynh").lastName("Vo").password("12345678")
				.dob(dob).build();

		userResponse = UserResponse.builder().id("asb408cf23").username("test").firstName("Quynh").lastName("Vo")
				.dob(dob).build();

		user = User.builder().id("asb408cf23").username("test").firstName("Quynh").lastName("Vo").password("12345678").dob(dob)
				.build();

	}

	@Test
	void createValidUser_success() {
		when(userRepo.existsByUsername("existingUsername")).thenReturn(false);
		when(userRepo.save(user)).thenReturn(user);
		when(roleRepo.findAllById(request.getRoles())).thenReturn(new ArrayList());
		when(userMapper.toUser(request)).thenReturn(user);
		when(userMapper.toUserResponse(user)).thenReturn(userResponse);
		
		var response = userService.createUser(request);
		
		assertThat(response).isEqualTo(userResponse);
	}
}