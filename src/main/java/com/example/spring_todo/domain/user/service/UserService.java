package com.example.spring_todo.domain.user.service;

import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.dto.UserLoginRequestDto;
import com.example.spring_todo.domain.user.dto.UserLoginResponseDto;
import com.example.spring_todo.domain.user.dto.UserSignUpRequestDto;
import com.example.spring_todo.domain.user.dto.UserSignUpResponseDto;
import com.example.spring_todo.domain.user.repository.UserRepository;
import com.example.spring_todo.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
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

        return new UserLoginResponseDto(email, accessToken);
    }
}
