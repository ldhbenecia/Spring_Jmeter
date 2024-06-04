package com.example.spring_todo.domain.todo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String contents;
    private int likes;
    private Boolean isCompleted;
}
