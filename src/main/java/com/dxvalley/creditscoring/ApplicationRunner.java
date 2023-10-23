package com.dxvalley.creditscoring;

import com.dxvalley.creditscoring.userManager.role.Role;
import com.dxvalley.creditscoring.userManager.role.RoleRepository;
import com.dxvalley.creditscoring.userManager.user.UserRepository;
import com.dxvalley.creditscoring.userManager.user.Users;
import com.dxvalley.creditscoring.utils.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "database", name = "seed", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Initializes the database with preloaded data upon application startup.
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            try {
                // Create and save roles
                List<Role> roles = createRoles();
                List<Role> savedRoles = roleRepository.saveAll(roles);

                // Create and save user
                Users johnDoe = createUser(savedRoles.get(0));
                userRepository.save(johnDoe);

                log.info("ApplicationRunner => Preloaded authority, roles, and user");
            } catch (Exception ex) {
                log.error("ApplicationRunner Preloading Error: {}", ex.getMessage());
                throw new RuntimeException("ApplicationRunner Preloading Error ", ex);
            }
        };
    }


    private List<Role> createRoles() {
        Role admin = new Role("ADMIN", "Oversees app functionality, user access, and settings.");
        Role owner = new Role("OWNER", "Key decision-maker with strategic authority.");
        Role teller = new Role("TELLER", "Analyzes credit apps for lending decisions.");

        return List.of(admin, owner, teller);
    }


    private Users createUser(Role role) {
        return Users.builder()
                .username("user@coop.com")
                .password(passwordEncoder.encode("123456"))
                .fullName("John Doe")
                .phoneNumber("+251912345678")
                .organizationId("1234")
                .role(role)
                .userStatus(Status.ACTIVE)
                .isEnabled(true)
                .createdBy("System")
                .build();
    }
}