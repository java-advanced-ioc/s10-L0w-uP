package com.softserve.itacademy.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final List<ToDo> todoLists = new ArrayList<>();
    private UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ToDo addTodo(User user, ToDo todo) {
        // TODO
        if (user == null || todo == null || todo.getTitle() == null) {
            return null;
        }

        String normalizedTitle = todo.getTitle().trim();
        todo.setTitle(normalizedTitle);
        todo.setOwner(user);

        if (todoLists.contains(todo)) {
            return null;
        }
        todoLists.add(todo);
        return todo;
    }
    @Override
    public ToDo updateTodo(ToDo todo) {
        // TODO
        if (todo == null || todo.getOwner() == null || todo.getTitle() == null) {
            return null;
        }

        int index = todoLists.indexOf(todo);
        if (index != -1) {
            todoLists.set(index, todo);
            return todo;
        }
        return null;
    }
    @Override
    public void deleteTodo(ToDo todo) {
        // TODO
        if (todo != null) {
            todoLists.remove(todo);
        }
    }
    @Override
    public List<ToDo> getAll() {
        // TODO
        return new ArrayList<>(todoLists);
    }
    @Override
    public List<ToDo> getByUser(User user) {
        // TODO
        if (user == null) {
            return new ArrayList<>();
        }
        return todoLists.stream()
                .filter(t -> t.getOwner().equals(user))
                .collect(Collectors.toList());
    }
    @Override
    public ToDo getByUserTitle(User user, String title) {
        // TODO
        if (user == null || title == null) {
            return null;
        }
        String normalizedTitle = title.trim();
        return todoLists.stream()
                .filter(t -> t.getOwner().equals(user) &&
                        t.getTitle().equalsIgnoreCase(normalizedTitle))
                .findFirst()
                .orElse(null);
    }
}
