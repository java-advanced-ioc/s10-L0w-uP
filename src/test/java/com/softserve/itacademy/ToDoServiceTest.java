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
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ToDoServiceTest {

    private AnnotationConfigApplicationContext context;
    private ToDoService toDoService;
    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(UserServiceImpl.class);
        context.register(ToDoServiceImpl.class);
        context.refresh();
        userService = context.getBean(UserService.class);
        toDoService = context.getBean(ToDoService.class);
        testUser = new User("John", "Doe", "john@example.com", "pass", null);
        userService.addUser(testUser);
    }

    @AfterEach
    void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    void addTodo_shouldAttachToOwner_andBeReturnedByGetByUser() {
        ToDo todo = new ToDo("Work", LocalDateTime.now(), null);
        toDoService.addTodo(testUser, todo);

        List<ToDo> result = toDoService.getByUser(testUser);
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0).getOwner());
    }

    @Test
    void addTodo_shouldReturnNull_whenInvalid() {
        assertNull(toDoService.addTodo(null, new ToDo()), "User cannot be null");
        assertNull(toDoService.addTodo(testUser, null), "ToDo cannot be null");
    }

    @Test
    void addTodo_shouldEnforceUniqueTitle_perOwner() {
        toDoService.addTodo(testUser, new ToDo("Shopping", LocalDateTime.now(), null));

        ToDo duplicate = new ToDo("  Shopping  ", LocalDateTime.now(), null);
        assertNull(toDoService.addTodo(testUser, duplicate), "Duplicate title (even with spaces) must be rejected");
    }

    @Test

    void updateTodo_shouldReplaceExistingByTitleWithinOwner() {
        ToDo todo = toDoService.addTodo(testUser, new ToDo("Fitness", LocalDateTime.now(), null));
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);
        todo.setCreatedAt(newDate);

        ToDo updated = toDoService.updateTodo(todo);
        assertNotNull(updated);
        assertEquals(newDate, toDoService.getByUserTitle(testUser, "Fitness").getCreatedAt());
    }

    @Test
    void updateTodo_shouldReturnNull_whenNotFound_orInvalid() {
        assertNull(toDoService.updateTodo(null));
        assertNull(toDoService.updateTodo(new ToDo("Non-existent", LocalDateTime.now(), testUser)));
    }

    @Test
    void deleteTodo_shouldRemoveFromOwnerList() {
        ToDo todo = toDoService.addTodo(testUser, new ToDo("DeleteMe", LocalDateTime.now(), null));
        toDoService.deleteTodo(todo);
        assertEquals(0, toDoService.getByUser(testUser).size());
    }

    @Test
    void getAll_shouldReturnCopy() {
        toDoService.addTodo(testUser, new ToDo("Task", LocalDateTime.now(), null));
        List<ToDo> all = toDoService.getAll();

        try {
            all.clear(); // Attempting to modify the returned list
        } catch (UnsupportedOperationException ignored) {}

        assertNotEquals(0, toDoService.getAll().size(), "Modification of returned list should not affect internal storage");
    }

    @Test
    void getByUser_shouldReturnCopy_orEmptyWhenNone() {
        assertTrue(toDoService.getByUser(new User()).isEmpty());
    }

    @Test
    void getByUserTitle_shouldReturnToDo_orNull() {
        toDoService.addTodo(testUser, new ToDo("FindMe", LocalDateTime.now(), null));
        assertNotNull(toDoService.getByUserTitle(testUser, "FindMe"));
        assertNull(toDoService.getByUserTitle(testUser, "Missing"));
    }
}
