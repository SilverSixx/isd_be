package com.example.demo.modules.auth.services;

import com.example.demo.modules.admin.AdminRepository;
import com.example.demo.modules.auth.dtos.AuthResponseDto;
import com.example.demo.modules.auth.dtos.LoginDto;
import com.example.demo.modules.teachers.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto signin(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        final var admin = adminRepository.findByUsername(request.getUsername())
                .orElse(null);
        // If not found in Admin repository, try to find in Teacher repository
        if (admin == null) {
            final var teacher = teacherRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

            // Generate JWT token for teacher
            final var jwt = jwtService.generateToken(teacher);
            return AuthResponseDto.builder()
                    .isError(false)
                    .message("Teacher login successful.")
                    .data(teacher)
                    .token(jwt)
                    .build();
        }
        final var jwt = jwtService.generateToken(admin);
        return AuthResponseDto.builder()
                .isError(false)
                .message("Admin login successful.")
                .data(admin)
                .token(jwt)
                .build();
    }

}
