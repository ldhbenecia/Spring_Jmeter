package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.dto.TodoDto;
import com.example.spring_todo.domain.todo.dto.TodoRequestDto;
import com.example.spring_todo.domain.todo.dto.TodoResponseDto;
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

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;

    private User testUser;
    private Todo testTodo;

    @BeforeEach
    public void before() {
        System.out.println("TodoService Test 사전 작업을 진행합니다.");

        testUser = userRepository.save(new User("test@example.com", "테스트 닉네임", "1234"));
        testTodo = new Todo();
        testTodo.setContents("테스트 목업 투두");
        testTodo.setUser(testUser);
        testTodo = todoRepository.save(testTodo);
    }

    @AfterEach
    public void after() {
        System.out.println("TodoService Test 테스트가 종료되었습니다.");
    }


    @Test
    @DisplayName("로그인 사용자의 TODO 조회 테스트")
    public void getTodosByCurrentUser() throws Exception {
        // given
        Long userId = testUser.getId();

        // when
        List<Todo> todos = todoRepository.findByUserId(userId);
        List<TodoDto> todoDtos = todos.stream()
                .map(TodoDto::new)
                .collect(Collectors.toList());

        // then
        Assertions.assertThat(todoDtos).isNotEmpty();
        Assertions.assertThat(todoDtos.get(0).getContents().equals("테스트 목업 투두"));
    }

    @Test
    @DisplayName("로그인 사용자 TODO 작성 테스트")
    public void createTodoV2() throws Exception {
        // given
        Long userId = testUser.getId();
        TodoRequestDto requestDto = new TodoRequestDto();
        requestDto.setContents("새로운 Todo");

        // when
        TodoResponseDto createdTodo = todoService.createTodoV2(requestDto, userId);

        // then
        Assertions.assertThat(createdTodo.getContents().equals("새로운 Todo"));
    }

    @Test
    @DisplayName("로그인 사용자 TODO 수정 테스트")
    public void updateTodoV2() throws Exception {
        // given
        Long userId = testUser.getId();
        Long todoId = testTodo.getId();
        TodoRequestDto requestDto = new TodoRequestDto();
        requestDto.setContents("수정된 Todo");

        // when
        TodoResponseDto updatedTodo = todoService.updateTodoV2(todoId, requestDto, userId);

        // then
        Assertions.assertThat(updatedTodo.getContents().equals("수정된 Todo"));
    }

    @Test
    @DisplayName("로그인 사용자 TODO 제거 테스트")
    public void deleteTodoV2() throws Exception {
        // given
        Long userId = testUser.getId();
        Long todoId = testTodo.getId();

        // when
        todoService.deleteTodoV2(todoId, userId);

        // then
        Assertions.assertThat(todoRepository.findById(todoId)).isEmpty();
    }
}