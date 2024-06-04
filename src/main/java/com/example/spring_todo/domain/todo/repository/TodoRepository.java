package com.example.spring_todo.domain.todo.repository;

import com.example.spring_todo.domain.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
