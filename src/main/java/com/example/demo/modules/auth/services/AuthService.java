package com.example.demo.modules.auth.services;

import com.example.demo.modules.admin.AdminRepository;
import com.example.demo.modules.auth.dtos.AuthResponseDto;
import com.example.demo.modules.auth.dtos.LoginDto;
import com.example.demo.modules.parents.ParentRepository;
import com.example.demo.modules.teachers.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto signin(LoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);
        return AuthResponseDto.builder()
                .isError(false)
                .message("Đăng nhập thành công.")
                .data(userDetails)
                .token(jwt)
                .build();

    }

    public AuthResponseDto logout() {
        SecurityContextHolder.clearContext();
        return AuthResponseDto.builder()
                .isError(false)
                .message("Đăng xuất thành công.")
                .build();
    }

}