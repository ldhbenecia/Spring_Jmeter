package com.example.spring_todo.domain.todo.controller;

import com.example.spring_todo.domain.todo.dto.TodoDto;
import com.example.spring_todo.domain.todo.dto.TodoRequestDto;
import com.example.spring_todo.domain.todo.dto.TodoResponseDto;
import com.example.spring_todo.domain.todo.service.TodoService;
import com.example.spring_todo.global.auth.service.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 전역 Todo 조회
     */
    @GetMapping("/api/v1/todo")
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * 현재 사용자 Todo 조회
     */
    @GetMapping("/api/v2/todo")
    public ResponseEntity<List<TodoDto>> getTodosByCurrentUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long currentUserId = principalDetails.getId();
        List<TodoDto> todos = todoService.getTodosByCurrentUser(currentUserId);
        return ResponseEntity.ok(todos);
    }

    /**
     * 전역 Todo 작성
     */
    @PostMapping("/api/v1/todo")
    public ResponseEntity<TodoResponseDto> createTodoV1(@RequestBody TodoRequestDto requestDto) {
        TodoResponseDto createdTodo = todoService.createTodoV1(requestDto);
        return ResponseEntity.ok(createdTodo);
    }

    /**
     * 현재 사용자 Todo 작성
     */
    @PostMapping("/api/v2/todo")
    public ResponseEntity<TodoResponseDto> createTodoV2(@RequestBody @Valid TodoRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long currentUserId = principalDetails.getId();
        TodoResponseDto createdTodo = todoService.createTodoV2(requestDto, currentUserId);
        return ResponseEntity.ok(createdTodo);
    }

    /**
     * 전역 Todo 수정
     */
    @PutMapping("/api/v1/todo/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoV1(@PathVariable("id") Long id, @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto updatedTodo = todoService.updateTodoV1(id, requestDto);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * 현재 사용자 Todo 수정
     */
    @PutMapping("/api/v2/todo/{id}")
    public ResponseEntity<TodoResponseDto> updateTodoV2(@PathVariable("id") Long id, @RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long currentUserId = principalDetails.getId();
        TodoResponseDto updatedTodo = todoService.updateTodoV2(id, requestDto, currentUserId);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * 전역 Todo 삭제
     */
    @DeleteMapping("/api/v1/todo/{id}")
    public ResponseEntity<Void> deleteTodoV1(@PathVariable("id") Long id) {
        todoService.deleteTodoV1(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 현재 사용자 Todo 삭제
     */
    @DeleteMapping("/api/v2/todo/{id}")
    public ResponseEntity<Void> deleteTodoV2(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long currentUserId = principalDetails.getId();
        todoService.deleteTodoV2(id, currentUserId);
        return ResponseEntity.noContent().build();
    }
}
