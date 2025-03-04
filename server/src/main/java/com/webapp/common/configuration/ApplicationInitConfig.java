package com.webapp.common.configuration;

import com.webapp.model.Role;
import com.webapp.model.User;
import com.webapp.module.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Configuration
public class ApplicationInitConfig {

    static final String ADMIN_USER_NAME = "admin";

    static final String ADMIN_PASSWORD = "admin";
    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(
            UserRepository userRepository) {
        log.warn("Initializing application...");
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user =
                        User.builder()
                                .username(ADMIN_USER_NAME)
                                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                                .roles(Collections.singletonList(Role.ADMIN))
                                .build();
                userRepository.save(user);
                log.warn("Admin user has been created with default password; please change it");
            }
        };
    }
}
