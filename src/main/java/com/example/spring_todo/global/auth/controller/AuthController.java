package com.example.spring_todo.global.auth.controller;

import com.example.spring_todo.global.auth.service.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/api/login/test")
    public void loginTest(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails = " + principalDetails); // principalDetails = com.example.spring_todo.global.auth.service.PrincipalDetails@xxxxx
        System.out.println("User email: " + principalDetails.getUsername()); // User email: test@example.com
        System.out.println("principalDetails User Id = " + principalDetails.getId()); // User Id = 1

    }
}
