package com.softserve.itacademy;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToDoServiceTest {

    private AnnotationConfigApplicationContext context;
    private ToDoService toDoService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(UserServiceImpl.class);
        context.register(ToDoServiceImpl.class);
        context.refresh();
        userService = context.getBean(UserService.class);
        toDoService = context.getBean(ToDoService.class);
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    void addTodo_shouldAttachToOwner_andBeReturnedByGetByUser() {

    }

    @Test

    void addTodo_shouldReturnNull_whenInvalid() {

    }

    @Test

    void addTodo_shouldEnforceUniqueTitle_perOwner() {

    }

    @Test

    void updateTodo_shouldReplaceExistingByTitleWithinOwner() {

    }

    @Test
    void updateTodo_shouldReturnNull_whenNotFound_orInvalid() {

    }

    @Test
    void deleteTodo_shouldRemoveFromOwnerList() {

    }

    @Test
    void getAll_shouldReturnCopy() {

    }

    @Test
    void getByUser_shouldReturnCopy_orEmptyWhenNone() {

    }

    @Test
    void getByUserTitle_shouldReturnToDo_orNull() {

    }
}
