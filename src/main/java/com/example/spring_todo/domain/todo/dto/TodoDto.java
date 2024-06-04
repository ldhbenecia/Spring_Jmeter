package com.example.spring_todo.domain.todo.dto;

import com.example.spring_todo.domain.todo.domain.Todo;
import lombok.Getter;

@Getter
public class TodoDto {
    private String contents;
    private int likes;
    private Boolean isCompleted;

    public TodoDto(Todo todo) {
        this.contents = todo.getContents();
        this.likes = todo.getLikes();
        this.isCompleted = todo.getIsCompleted();
    }
}
