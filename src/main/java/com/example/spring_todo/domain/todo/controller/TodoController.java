package com.example.spring_todo.domain.todo.controller;

import com.example.spring_todo.domain.todo.dto.TodoDto;
import com.example.spring_todo.domain.todo.dto.TodoRequestDto;
import com.example.spring_todo.domain.todo.dto.TodoResponseDto;
import com.example.spring_todo.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/api/todos")
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @PostMapping("/api/todo")
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        TodoResponseDto createdTodo = todoService.createTodo(requestDto);
        return ResponseEntity.ok(createdTodo);
    }

    @PutMapping("/api/todo/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable("id") Long id, @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto updatedTodo = todoService.updateTodo(id, requestDto);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/api/todo/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Redis 캐싱 사용 x
     * @param id
     */
    @PostMapping("/api/{id}/like")
    public ResponseEntity<Void> incrementLikesNoCaching(@PathVariable Long id) {
        todoService.incrementLikesNoCaching(id);
        return ResponseEntity.ok().build();
    }

}
