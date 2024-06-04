package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.dto.TodoDto;
import com.example.spring_todo.domain.todo.dto.TodoRequestDto;
import com.example.spring_todo.domain.todo.dto.TodoResponseDto;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        return todos.stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
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
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto) {
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
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo Not Found");
        }
        todoRepository.deleteById(id);
    }

    @Transactional
    public void incrementLikesNoCaching(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.incrementLikes();
        todoRepository.save(todo);
    }
}
