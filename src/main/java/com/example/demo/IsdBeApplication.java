package com.example.demo;

import com.example.demo.modules.admin.Admin;
import com.example.demo.modules.admin.AdminRepository;
import com.example.demo.modules.auth.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class IsdBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsdBeApplication.class, args);
    }

    // init admin user
    @Bean
    CommandLineRunner init(AdminRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<Admin> admin = userRepository.findByUsername("admin");
            if (admin.isEmpty()) {
                Admin newAdmin = Admin.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(newAdmin);
            }
        };
    }


}
