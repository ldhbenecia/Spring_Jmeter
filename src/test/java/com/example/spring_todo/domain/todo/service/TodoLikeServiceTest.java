package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.repository.TodoLikeRepository;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TodoLikeServiceTest {

    @Autowired
    TodoLikeService todoLikeService;

    @Autowired
    TodoLikeRepository todoLikeRepository;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;

    private User testUser;
    private Todo testTodo;

    @BeforeEach
    public void before() {
        System.out.println("TodoLikeService Test 사전 작업을 진행합니다.");

        testUser = userRepository.save(new User("test@example.com", "테스트 닉네임", "1234"));
        testTodo = new Todo();
        testTodo.setContents("테스트 목업 투두");
        testTodo.setUser(testUser);
        testTodo = todoRepository.save(testTodo);
    }

    @AfterEach
    public void after() {
        System.out.println("TodoLikeService Test 테스트가 종료되었습니다.");
    }

    @Test
    @DisplayName("Todo 좋아요 기능 서비스 로직 테스트")
    public void likeTodo() throws Exception {
        // given
        Long todoId = testTodo.getId();
        Long userId = testUser.getId();

        // when
        todoLikeService.likeTodo(todoId, userId);

        // then
        Todo updatedTodo = todoRepository.findById(todoId).orElseThrow();
        Assertions.assertThat(updatedTodo.getLikes()).isEqualTo(1);
    }

    @Test
    @DisplayName("Todo 좋아요 취소 기능 서비스 로직 테스트")
    public void unlikeTodo() throws Exception {
        // given
        Long todoId = testTodo.getId();
        Long userId = testUser.getId();

        todoLikeService.likeTodo(todoId, userId);

        // when
        todoLikeService.unlikeTodo(todoId, userId);

        // then
        Todo updatedTodo = todoRepository.findById(todoId).orElseThrow();
        Assertions.assertThat(updatedTodo.getLikes()).isEqualTo(0);
    }
}