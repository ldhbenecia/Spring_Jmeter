package com.example.spring_todo.domain.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequestDto {

    @NotBlank
    private String contents;
}
