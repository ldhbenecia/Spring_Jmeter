package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.domain.TodoLike;
import com.example.spring_todo.domain.todo.repository.TodoLikeRepository;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoLikeService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoLikeRepository todoLikeRepository;

    @Transactional
    public void likeTodo(Long id, Long currentUserId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        boolean alreadyLiked = todoLikeRepository.existsByTodoIdAndUserId(id, currentUserId);
        if (alreadyLiked) {
            throw new RuntimeException("이미 좋아요를 눌렀습니다.");
        }

        TodoLike todoLike = new TodoLike();
        todoLike.setTodo(todo);
        todoLike.setUser(userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")));
        todoLikeRepository.save(todoLike);

        todo.incrementLikes();
        todoLikeRepository.save(todoLike);
    }

    @Transactional
    public void unlikeTodo(Long id, Long currentUserId) {
        todoLikeRepository.deleteByTodoIdAndUserId(id, currentUserId);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        todo.decrementLikes();
        todoRepository.save(todo);
    }
}
