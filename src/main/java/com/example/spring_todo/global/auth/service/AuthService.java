package com.example.spring_todo.global.auth.service;

import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import com.example.spring_todo.global.auth.dto.LoginRequestDto;
import com.example.spring_todo.global.auth.dto.LoginResponseDto;
import com.example.spring_todo.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto login(LoginRequestDto requestDto) {
        String accessToken = null;

        String email = requestDto.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String password = requestDto.getPassword();
        String encodedPassword = user.getPassword();
        boolean isMatched = passwordEncoder.matches(password, encodedPassword);
        if (!isMatched) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        accessToken = jwtTokenProvider.createToken(email);

        return new LoginResponseDto(email, accessToken);
    }
}
