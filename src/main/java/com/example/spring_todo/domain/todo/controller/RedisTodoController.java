package com.example.spring_todo.domain.todo.controller;

import com.example.spring_todo.domain.todo.service.RedisTodoLikeService;
import com.example.spring_todo.global.auth.service.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisTodoController {

    private final RedisTodoLikeService redisTodoLikeService;

    @PostMapping("/api/v2/{id}/like")
    public ResponseEntity<Void> likeTodo(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long currentUserId = principalDetails.getId();
        redisTodoLikeService.likeTodo(id, currentUserId);
        return ResponseEntity.ok().build();
    }
}
