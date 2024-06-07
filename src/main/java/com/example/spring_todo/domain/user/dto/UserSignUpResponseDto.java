package com.example.spring_todo.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserSignUpResponseDto {

    private Long id;
    private String email;
    private String nickname;
}
