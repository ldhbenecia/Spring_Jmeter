package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.dto.TodoDto;
import com.example.spring_todo.domain.todo.dto.TodoRequestDto;
import com.example.spring_todo.domain.todo.dto.TodoResponseDto;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import com.example.spring_todo.global.exception.CustomErrorException;
import com.example.spring_todo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
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
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_USER));

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
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_TODO));
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
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_TODO));

        if (!todo.getUser().getId().equals(currentUserId)) {
            throw new CustomErrorException(ErrorCode.UNAUTHORIZED_UPDATE_TODO);
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
            throw new CustomErrorException(ErrorCode.NOT_FOUND_TODO);
        }
        todoRepository.deleteById(id);
    }

    @Transactional
    public void deleteTodoV2(Long id, Long currentUserId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_TODO));

        if (!todo.getUser().getId().equals(currentUserId)) {
            throw new CustomErrorException(ErrorCode.UNAUTHORIZED_UPDATE_TODO);
        }

        todoRepository.deleteById(id);
    }
}
