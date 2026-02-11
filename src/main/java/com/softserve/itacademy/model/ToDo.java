package com.softserve.itacademy.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tasks")
@EqualsAndHashCode(of = {"title", "owner"})
public class ToDo {

    private String title;
    private LocalDateTime createdAt;
    private User owner;
    private List<Task> tasks = new ArrayList<>();

    public ToDo(String title, LocalDateTime createdAt, User owner) {
        this.title = title;
        this.createdAt = createdAt;
        this.owner = owner;
    }
}