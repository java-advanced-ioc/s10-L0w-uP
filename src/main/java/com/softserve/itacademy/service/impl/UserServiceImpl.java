package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {
        if (user == null) return null;
        if (user.getEmail() == null) return null;
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) return null;
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) return null;

        if (users.contains(user)) return null;
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user == null) return null;
        if (user.getEmail() == null) return null;

        int idx = users.indexOf(user);
        if (idx == -1) return null;

        User existing = users.get(idx);

        // validate first/last name: cannot be null or empty
        if (user.getFirstName() != null && !user.getFirstName().trim().isEmpty()) {
            existing.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null && !user.getLastName().trim().isEmpty()) {
            existing.setLastName(user.getLastName());
        }
        existing.setPassword(user.getPassword());

        if (user.getMyTodos() != null) {
            existing.setMyTodos(user.getMyTodos());
        }

        return existing;
    }

    @Override
    public void deleteUser(User user) {
        if (user == null) return;
        if (user.getEmail() == null) return;

        users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }
}
