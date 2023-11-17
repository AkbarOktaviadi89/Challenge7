package com.binarfud.challenge7.service;

import com.binarfud.challenge7.Model.UserDetailsImpl;
import com.binarfud.challenge7.Model.Users;
import com.binarfud.challenge7.Repository.UserRepository;
import com.binarfud.challenge7.Service.Impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "testuser";
        Users user = new Users();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        verify(userRepository).findByUsername(username);
    }

    @Test
    void testLoadUserByUsernameException() {
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        verify(userRepository).findByUsername(username);
    }
}
