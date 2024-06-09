package com.example.spring_todo.domain.todo.repository;

import com.example.spring_todo.domain.todo.domain.TodoLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoLikeRepository extends JpaRepository<TodoLike, Long> {

    boolean existsByTodoIdAndUserId(Long todoId, Long userId);

    Optional<TodoLike> findByTodoIdAndUserId(Long todoId, Long userId);
}
