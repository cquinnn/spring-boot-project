package com.webapp.user.service;

import com.webapp.model.User;
import com.webapp.module.user.dto.request.UserCreationRequest;
import com.webapp.module.user.dto.response.UserResponse;
import com.webapp.module.user.mapper.UserMapper;
import com.webapp.module.user.repository.UserRepository;
import com.webapp.module.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
    private UserMapper userMapper;
    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;
    private User user;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2001, 10, 10);
        request =
                UserCreationRequest.builder()
                        .username("test")
                        .firstName("Quynh")
                        .lastName("Vo")
                        .password("12345678")
                        .dob(dob)
                        .build();

        userResponse =
                UserResponse.builder()
                        .id("asb408cf23")
                        .username("test")
                        .firstName("Quynh")
                        .lastName("Vo")
                        .dob(dob)
                        .build();

        user =
                User.builder()
                        .id("asb408cf23")
                        .username("test")
                        .firstName("Quynh")
                        .lastName("Vo")
                        .password("12345678")
                        .dob(dob)
                        .build();

        user =
                User.builder()
                        .id("asb408cf23")
                        .username("test")
                        .firstName("Quynh")
                        .lastName("Vo")
                        .password("12345678")
                        .dob(dob)
                        .build();
    }

    @Test
    void createValidUser_success() {
        when(userRepo.existsByUsername("existingUsername")).thenReturn(false);
        when(userRepo.save(user)).thenReturn(user);
//    when(roleRepo.findAllById(request.getRoles())).thenReturn(new ArrayList());
        when(userMapper.toUser(request)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        var response = userService.createUser(request);

        assertThat(response).isEqualTo(userResponse);
    }
}
