package com.example.spring_todo.domain.user.service;

import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.dto.UserSignUpRequestDto;
import com.example.spring_todo.domain.user.dto.UserSignUpResponseDto;
import com.example.spring_todo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSignUpResponseDto createUser(UserSignUpRequestDto requestDto) {
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setNickname(requestDto.getNickname());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(encodedPassword);

        user = userRepository.save(user);

        return UserSignUpResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
