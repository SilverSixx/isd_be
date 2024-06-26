package com.example.demo.modules.auth;

import com.example.demo.modules.auth.dtos.AuthResponseDto;
import com.example.demo.modules.auth.dtos.LoginDto;
import com.example.demo.modules.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponseDto> logout() {
        return ResponseEntity.ok(authenticationService.logout());
    }


}
