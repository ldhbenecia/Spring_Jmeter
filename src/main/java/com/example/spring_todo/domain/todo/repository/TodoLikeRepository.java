package com.example.spring_todo.domain.todo.repository;

import com.example.spring_todo.domain.todo.domain.TodoLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoLikeRepository extends JpaRepository<TodoLike, Long> {

    void deleteByTodoIdAndUserId(Long todoId, Long userId);

    boolean existsByTodoIdAndUserId(Long todoId, Long userId);
}
