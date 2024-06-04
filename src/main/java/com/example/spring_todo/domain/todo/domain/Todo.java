package com.example.spring_todo.domain.todo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(length = 200)
    private String contents;

    private int likes;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    public void incrementLikes() {
        this.likes++;
    }

    public void toggleCompleted() {
        this.isCompleted = !this.isCompleted;
    }
}
