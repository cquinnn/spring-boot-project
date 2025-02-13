package com.webapp.common.configuration;

import com.webapp.model.Role;
import com.webapp.module.auth.repository.RoleRepository;
import com.webapp.module.user.entity.User;
import com.webapp.module.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Configuration
public class ApplicationInitConfig {
    @NonFinal
    static final String ADMIN_USER_NAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "admin";
    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(
            UserRepository userRepository, RoleRepository roleRepository) {
        log.warn("Initializing application...");
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                List<Role> roles = new ArrayList<>();
                Role role = Role.builder().name("ADMIN").build();
                Role role2 = Role.builder().name("USER").build();
                roleRepository.save(role);
                roleRepository.save(role2);
                roles.add(role);
                User user =
                        User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin"))
                                .roles(roles)
                                .build();
                userRepository.save(user);
                log.warn("Admin user has been created with default password; please change it");
            }
        };
    }
}
