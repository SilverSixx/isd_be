package com.example.demo.modules.auth;

import com.example.demo.modules.auth.dtos.JwtResponseDto;
import com.example.demo.modules.auth.dtos.LoginDto;
import com.example.demo.modules.auth.dtos.SignUpDto;
import com.example.demo.modules.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDto> signup(@RequestBody SignUpDto request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }
}
