package com.example.spring_todo.domain.todo.repository;

import com.example.spring_todo.domain.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUserId(Long userId);
}
