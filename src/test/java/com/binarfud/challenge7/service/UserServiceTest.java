package com.binarfud.challenge7.service;

import com.binarfud.challenge7.Model.Users;
import com.binarfud.challenge7.Repository.UserRepository;
import com.binarfud.challenge7.Service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAllUsers() {
        List<Users> users = new ArrayList<>();
        when(userRepository.getAllUsersAvailable()).thenReturn(users);

        List<Users> result = userService.findAllUsers();

        assertEquals(users, result);
        verify(userRepository).getAllUsersAvailable();
    }

    @Test
    void testFindByUsername() {
        String username = "testuser";
        Users user = new Users();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<Users> result = userService.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        Users user = new Users();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<Users> result = userService.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository).findById(userId);
    }

    @Test
    void testAddNewUser() {
        Users user = new Users();
        when(userRepository.save(user)).thenReturn(user);

        boolean result = userService.addNewUser(user);

        assertTrue(result);
        verify(userRepository).save(user);
    }

    @Test
    void testAddNewUserFail() {
        Users user = new Users();
        when(userRepository.save(user)).thenReturn(null);

        boolean result = userService.addNewUser(user);

        assertFalse(result);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void testDeleteAllUsers() {
        doNothing().when(userRepository).deleteAll();

        boolean result = userService.deleteAllUser();

        assertTrue(result);
        verify(userRepository).deleteAll();
    }

    @Test
    void testUpdateUserbyId() {
        Long userId = 1L;
        Users user = new Users();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userService.updateUserbyId(user);

        verify(userRepository).findById(userId);
        verify(userRepository).save(user);

        assertTrue(result);
    }


    @Test
    void testUpdateUserbyIdUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUserbyId(new Users());
        });

        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any());
    }
}
