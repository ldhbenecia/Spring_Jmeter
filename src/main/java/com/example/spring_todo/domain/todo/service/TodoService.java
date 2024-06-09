package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.dto.TodoDto;
import com.example.spring_todo.domain.todo.dto.TodoRequestDto;
import com.example.spring_todo.domain.todo.dto.TodoResponseDto;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import com.example.spring_todo.global.auth.service.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        return todos.stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());
    }

    public List<TodoDto> getTodosByCurrentUser(Long currentUserId) {
        return todoRepository.findByUserId(currentUserId)
                .stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoResponseDto createTodoV1(TodoRequestDto requestDto) {
        Todo todo = new Todo();
        todo.setContents(requestDto.getContents());

        Todo saveTodo = todoRepository.save(todo);

        return TodoResponseDto.builder()
                .id(saveTodo.getId())
                .contents(saveTodo.getContents())
                .likes(saveTodo.getLikes())
                .isCompleted(saveTodo.getIsCompleted())
                .build();
    }

    @Transactional
    public TodoResponseDto createTodoV2(TodoRequestDto requestDto, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("현재 사용자를 찾을 수 없습니다."));

        Todo todo = new Todo();
        todo.setContents(requestDto.getContents());
        todo.setUser(currentUser);

        Todo savedTodo = todoRepository.save(todo);

        return TodoResponseDto.builder()
                .id(savedTodo.getId())
                .contents(savedTodo.getContents())
                .likes(savedTodo.getLikes())
                .isCompleted(savedTodo.getIsCompleted())
                .build();
    }

    @Transactional
    public TodoResponseDto updateTodoV1(Long id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo Not Found"));
        todo.setContents(requestDto.getContents());

        Todo saveTodo = todoRepository.save(todo);

        return TodoResponseDto.builder()
                .id(saveTodo.getId())
                .contents(saveTodo.getContents())
                .likes(saveTodo.getLikes())
                .isCompleted(saveTodo.getIsCompleted())
                .build();
    }

    @Transactional
    public TodoResponseDto updateTodoV2(Long id, TodoRequestDto requestDto, Long currentUserId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo Not Found"));

        // 투두를 소유한 사용자인지 확인
        if (!todo.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("해당 투두를 업데이트할 권한이 없습니다.");
        }

        todo.setContents(requestDto.getContents());
        Todo saveTodo = todoRepository.save(todo);

        return TodoResponseDto.builder()
                .id(saveTodo.getId())
                .contents(saveTodo.getContents())
                .likes(saveTodo.getLikes())
                .isCompleted(saveTodo.getIsCompleted())
                .build();
    }

    @Transactional
    public void deleteTodoV1(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo Not Found");
        }
        todoRepository.deleteById(id);
    }

    @Transactional
    public void deleteTodoV2(Long id, Long currentUserId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo Not Found"));

        // 투두를 소유한 사용자인지 확인
        if (!todo.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("해당 투두를 업데이트할 권한이 없습니다.");
        }

        todoRepository.deleteById(id);
    }
}
