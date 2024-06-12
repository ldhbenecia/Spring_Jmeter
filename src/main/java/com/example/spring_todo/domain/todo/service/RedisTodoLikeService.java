package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.domain.TodoLike;
import com.example.spring_todo.domain.todo.repository.TodoLikeRepository;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import com.example.spring_todo.global.exception.CustomErrorException;
import com.example.spring_todo.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTodoLikeService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoLikeRepository todoLikeRepository;
    private final RedissonClient redissonClient;

    public void likeTodoWithLock(Long id, Long currentUserId) {
        String lockKey = "todo-likes-lock-" + id;
        RLock rLock = redissonClient.getLock(lockKey);

        try {
            boolean available = rLock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available) {
                throw new CustomErrorException(ErrorCode.CAN_NOT_USE_LOCK);
            }

            try {
                likeTodoTransactional(id, currentUserId);
            } finally {
                if (rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }

        } catch (InterruptedException e) {
            throw new CustomErrorException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void likeTodoTransactional(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_USER));
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NOT_FOUND_TODO));

        boolean alreadyLiked = todoLikeRepository.existsByTodoIdAndUserId(id, currentUserId);
        if (alreadyLiked) {
            throw new CustomErrorException(ErrorCode.ALREADY_LIKED);
        }

        TodoLike todoLike = new TodoLike();
        todoLike.setTodo(todo);
        todoLike.setUser(currentUser);
        todoLikeRepository.save(todoLike);

        todo.incrementLikes();
        todoRepository.save(todo);
    }
}
