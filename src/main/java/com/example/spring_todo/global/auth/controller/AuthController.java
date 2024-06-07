package com.example.spring_todo.global.auth.controller;

import com.example.spring_todo.global.auth.dto.LoginRequestDto;
import com.example.spring_todo.global.auth.dto.LoginResponseDto;
import com.example.spring_todo.global.auth.service.AuthService;
import com.example.spring_todo.global.auth.service.PrincipleDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/api/login/test")
    public void loginTest(@AuthenticationPrincipal PrincipleDetails principleDetails) {
        System.out.println("principleDetails = " + principleDetails); // principleDetails = com.example.spring_todo.global.auth.service.PrincipleDetails@152810cb
        System.out.println("User email: " + principleDetails.getUsername()); // User email: test@example.com

    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
