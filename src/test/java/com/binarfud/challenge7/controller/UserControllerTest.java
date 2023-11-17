package com.binarfud.challenge7.controller;

import com.binarfud.challenge7.Controller.UserController;
import com.binarfud.challenge7.Model.Users;
import com.binarfud.challenge7.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUser() {
        List<Users> users = new ArrayList<>();
        users.add(new Users());

        when(userService.findAllUsers()).thenReturn(users);

        ResponseEntity<List<Users>> response = userController.getAllUser(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetAllUserByUsername() {
        Optional<Users> users = Optional.of(new Users());

        when(userService.findByUsername("test")).thenReturn(users);

        ResponseEntity<List<Users>> response = userController.getAllUser("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllUserEmpty() {
        List<Users> users = new ArrayList<>();

        when(userService.findAllUsers()).thenReturn(users);

        ResponseEntity<List<Users>> response = userController.getAllUser(null);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetUserById() {
        long userId = 1L;
        Users user = new Users();
        when(userService.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<Users> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetUserByIdNotFound() {
        long userId = 1L;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Users> response = userController.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateUser() {
        Users user = new Users();
        when(userService.addNewUser(user)).thenReturn(true);

        ResponseEntity<Users> response = userController.creteUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateUserError() {
        Users user = new Users();
        when(userService.addNewUser(user)).thenReturn(false);

        ResponseEntity<Users> response = userController.creteUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateUser() {
        long userId = 1L;
        Users user = new Users();
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        when(userService.updateUserbyId(user)).thenReturn(true);

        ResponseEntity<Users> response = userController.updateUser(userId, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateUserNotFound() {
        long userId = 1L;
        when(userService.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Users> response = userController.updateUser(userId, new Users());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateUserError() {
        long userId = 1L;
        Users user = new Users();
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        when(userService.updateUserbyId(user)).thenReturn(false);

        ResponseEntity<Users> response = userController.updateUser(userId, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUser() {
        when(userService.deleteAllUser()).thenReturn(true);

        ResponseEntity response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteUserError() {
        when(userService.deleteAllUser()).thenReturn(false);

        ResponseEntity response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
