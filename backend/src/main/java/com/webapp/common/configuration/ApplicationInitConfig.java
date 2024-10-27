package com.webapp.common.configuration;

import com.webapp.auth.entity.Role;
import com.webapp.auth.repository.RoleRepository;
import com.webapp.user.entity.User;
import com.webapp.user.repository.UserRepository;
import java.util.HashSet;
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

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Configuration
public class ApplicationInitConfig {
  PasswordEncoder passwordEncoder;
  @NonFinal static final String ADMIN_USER_NAME = "admin";
  @NonFinal static final String ADMIN_PASSWORD = "admin";

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
        var roles = new HashSet<Role>();
        Role role = Role.builder().name("ADMIN").build();
        roleRepository.save(role);
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
