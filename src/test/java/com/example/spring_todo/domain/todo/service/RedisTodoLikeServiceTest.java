package com.example.spring_todo.domain.todo.service;

import com.example.spring_todo.domain.todo.domain.Todo;
import com.example.spring_todo.domain.todo.repository.TodoRepository;
import com.example.spring_todo.domain.user.domain.User;
import com.example.spring_todo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RedisTodoLikeServiceTest {

    @Autowired
    private RedisTodoLikeService redisTodoLikeService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private Todo testTodo;
    private List<User> testUsers = new ArrayList<>();

    @BeforeEach
    public void before() {
        System.out.println("RedisTodoLikeService Test 사전 작업을 진행합니다.");

        for (int i = 1; i <= 100; i++) {
            User user = new User("user" + i + "@example.com", "user" + i, "password");
            userRepository.save(user);
            testUsers.add(user);
            System.out.println("Created user: " + user.getId());
        }

        testTodo = new Todo();
        testTodo.setContents("테스트 목업 투두");
        todoRepository.save(testTodo);
    }

    @AfterEach
    public void after() {
        System.out.println("RedisTodoLikeService Test 테스트가 종료되었습니다.");
    }

    @Test // 아직 동시성 문제에 대한 해결이 되지 않아서 정확하지 않음 | 240611 ldhbenecia
    @DisplayName("100명의 유저가 투두에 좋아요를 클릭합니다.")
    public void testConcurrentLikes() throws Exception {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (User user : testUsers) {
            User currentUser = userRepository.findById(user.getId()).orElse(null);
            executorService.execute(() -> {
                try {
                    if (currentUser != null) {
                        redisTodoLikeService.likeTodo(testTodo.getId(), currentUser.getId());
                    } else {
                        System.out.println("User not found: " + user.getId());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        Todo likedTodo = todoRepository.findById(testTodo.getId()).orElse(null);
        assertNotNull(likedTodo);
        assertEquals(100, likedTodo.getLikes());
    }
}
