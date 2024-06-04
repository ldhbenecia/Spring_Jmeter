package com.example.spring_todo.domain.todo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponseDto {
    private Long id;
    private String contents;
    private int likes;
    private Boolean isCompleted;
}
