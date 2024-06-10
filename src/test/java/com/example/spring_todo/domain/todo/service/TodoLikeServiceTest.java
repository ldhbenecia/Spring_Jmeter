package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.repository.TodoLikeRepository;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import com.example.spring_todo.global.exception.CustomErrorException;
import com.example.spring_todo.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Nested
    @DisplayName("좋아요 기능 테스트")
    class LikeTests {

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
        @DisplayName("이미 좋아요를 누른 Todo에 다시 좋아요를 누를 때 예외 발생")
        public void likeTodo_AlreadyLiked() throws Exception {
            // given
            Long todoId = testTodo.getId();
            Long userId = testUser.getId();

            // when
            todoLikeService.likeTodo(todoId, userId);

            // then
            CustomErrorException exception = assertThrows(CustomErrorException.class, () -> {
                todoLikeService.likeTodo(todoId, userId);
            });

            Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ALREADY_LIKED);
        }
    }

    @Nested
    @DisplayName("좋아요 취소 기능 테스트")
    class UnlikeTests {

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

        @Test
        @DisplayName("좋아요 기록이 없는 경우 예외 처리")
        public void unlikeTodo_NotLiked() throws Exception {
            // given
            Long todoId = testTodo.getId();
            Long userId = testUser.getId();

            // then
            CustomErrorException exception = assertThrows(CustomErrorException.class, () -> {
                todoLikeService.unlikeTodo(todoId, userId);
            });

            Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_TODO_LIKE);
        }
    }
}