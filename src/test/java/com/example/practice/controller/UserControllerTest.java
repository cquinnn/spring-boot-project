package com.example.practice.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.practice.dto.request.UserCreationRequest;
import com.example.practice.dto.response.UserResponse;
import com.example.practice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	private UserCreationRequest request;
	private UserResponse userResponse;
	private LocalDate dob;

	@BeforeEach
	void initData() {
		dob = LocalDate.of(2001, 10, 10);
		request = UserCreationRequest.builder().username("test").firstName("Quynh").lastName("Vo").password("12345678")
				.dob(dob).build();
		userResponse = UserResponse.builder().id("asb408cf23").username("test").firstName("Quynh").lastName("Vo")
				.dob(dob).build();
	}

	@Test
	void createUser_validRequest_success() throws Exception {
		// GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	
		Mockito.when(userService.createUser(request))
			.thenReturn(userResponse);
		
		// WHEN

		mockMvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1000))
				.andExpect(MockMvcResultMatchers.jsonPath("result.id").value("asb408cf23"))
		;
	}
	@Test
	void createUser_usernameInvalid_fail() throws Exception {
		// GIVEN
		request.setUsername("hi");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	
		Mockito.when(userService.createUser(request))
			.thenReturn(userResponse);
		
		// WHEN

		mockMvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("code")
						.value(1003))
				.andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 3 characters"))
		;
	}
}
