package com.example.spring_todo.global.auth.controller;

import com.example.spring_todo.global.auth.service.PrincipleDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/api/login/test")
    public void loginTest(@AuthenticationPrincipal PrincipleDetails principleDetails) {
        System.out.println("principleDetails = " + principleDetails); // principleDetails = com.example.spring_todo.global.auth.service.PrincipleDetails@xxxxx
        System.out.println("User email: " + principleDetails.getUsername()); // User email: test@example.com

    }
}
