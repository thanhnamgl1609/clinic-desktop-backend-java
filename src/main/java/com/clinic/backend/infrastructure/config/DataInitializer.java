package com.clinic.backend.infrastructure.config;

import com.clinic.backend.domain.permission.model.Permission;
import com.clinic.backend.domain.permission.repository.PermissionRepository;
import com.clinic.backend.domain.role.model.Role;
import com.clinic.backend.domain.role.repository.RoleRepository;
import com.clinic.backend.domain.user.model.User;
import com.clinic.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            if (userRepository.count() == 0) {
                log.info("Initializing default data...");

                // Create and save permissions first
                Permission createUser = permissionRepository.save(Permission.builder()
                        .name("create_user")
                        .description("Permission to create users")
                        .build());

                Permission editUser = permissionRepository.save(Permission.builder()
                        .name("edit_user")
                        .description("Permission to edit users")
                        .build());

                Permission viewPatient = permissionRepository.save(Permission.builder()
                        .name("view_patient_detail")
                        .description("Permission to view patient details")
                        .build());

                Permission createPatient = permissionRepository.save(Permission.builder()
                        .name("create_patient")
                        .description("Permission to create patients")
                        .build());

                // Create Admin role with permissions
                Role adminRole = roleRepository.save(Role.builder()
                        .name("ADMIN")
                        .description("Administrator role with full access")
                        .permissions(new HashSet<>(Arrays.asList(createUser, editUser, viewPatient, createPatient)))
                        .build());

                // Create Employee role
                Role employeeRole = roleRepository.save(Role.builder()
                        .name("EMPLOYEE")
                        .description("Employee role with limited access")
                        .permissions(new HashSet<>(Arrays.asList(viewPatient, createPatient)))
                        .build());

                // Create default admin user
                User admin = User.builder()
                        .username("admin")
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .fullName("System Administrator")
                        .email("admin@clinic.com")
                        .isActive(true)
                        .isVerified(true)
                        .roles(new HashSet<>(Arrays.asList(adminRole)))
                        .build();

                userRepository.save(admin);

                // Create default employee user
                User employee = User.builder()
                        .username("employee")
                        .passwordHash(passwordEncoder.encode("employee123"))
                        .fullName("Test Employee")
                        .email("employee@clinic.com")
                        .isActive(true)
                        .isVerified(true)
                        .roles(new HashSet<>(Arrays.asList(employeeRole)))
                        .build();

                userRepository.save(employee);

                log.info("Default data initialized successfully!");
                log.info("Admin user - username: admin, password: admin123");
                log.info("Employee user - username: employee, password: employee123");
            } else {
                log.info("Data already exists, skipping initialization.");
            }
        };
    }
}
