package com.binarfud.challenge7.controller;

import com.binarfud.challenge7.Config.JwtUtils;
import com.binarfud.challenge7.Controller.AuthController;
import com.binarfud.challenge7.Enum.ERole;
import com.binarfud.challenge7.Model.Request.LoginRequest;
import com.binarfud.challenge7.Model.Request.SignUpRequest;
import com.binarfud.challenge7.Model.Response.JwtResponse;
import com.binarfud.challenge7.Model.Response.MessageResponse;
import com.binarfud.challenge7.Model.Roles;
import com.binarfud.challenge7.Model.UserDetailsImpl;
import com.binarfud.challenge7.Model.Users;
import com.binarfud.challenge7.Repository.RoleRepository;
import com.binarfud.challenge7.Repository.UserRepository;
import com.binarfud.challenge7.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository usersRepository;

    @Mock
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        Authentication authentication = mock(Authentication.class);
        JwtResponse jwtResponse = new JwtResponse();
        UserDetailsImpl userDetails = new UserDetailsImpl();
        List<GrantedAuthority> authorities = new ArrayList<>();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("token");

        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testAuthenticateUserError() {
        LoginRequest loginRequest = new LoginRequest();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException());

        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testRegisterUser() {
        SignUpRequest signUpRequest = new SignUpRequest();
        Users user = new Users();
        Set<String> roles = new HashSet<>();
        Set<Roles> userRoles = new HashSet<>();

        when(usersRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(usersRepository.existsByEmailAddress(signUpRequest.getEmail())).thenReturn(false);
        when(roleRepository.findByRoleName(ERole.ROLE_CUSTOMER)).thenReturn(Optional.of(new Roles()));
        when(userService.addNewUser(any(Users.class))).thenReturn(true);

        ResponseEntity<MessageResponse> response = authController.registerUser(signUpRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testRegisterUserUsernameExists() {
        SignUpRequest signUpRequest = new SignUpRequest();
        Users user = new Users();
        Set<String> roles = new HashSet<>();
        Set<Roles> userRoles = new HashSet<>();

        when(usersRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);

        ResponseEntity<MessageResponse> response = authController.registerUser(signUpRequest);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testRegisterUserEmailExists() {
        SignUpRequest signUpRequest = new SignUpRequest();
        Users user = new Users();
        Set<String> roles = new HashSet<>();
        Set<Roles> userRoles = new HashSet<>();

        when(usersRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(usersRepository.existsByEmailAddress(signUpRequest.getEmail())).thenReturn(true);

        ResponseEntity<MessageResponse> response = authController.registerUser(signUpRequest);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testRegisterUserError() {
        SignUpRequest signUpRequest = new SignUpRequest();
        Users user = new Users();
        Set<String> roles = new HashSet<>();
        Set<Roles> userRoles = new HashSet<>();

        when(usersRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(usersRepository.existsByEmailAddress(signUpRequest.getEmail())).thenReturn(false);
        when(roleRepository.findByRoleName(ERole.ROLE_CUSTOMER)).thenReturn(Optional.of(new Roles()));
        when(userService.addNewUser(any(Users.class))).thenThrow(new RuntimeException());

        ResponseEntity<MessageResponse> response = authController.registerUser(signUpRequest);

        assertEquals(400, response.getStatusCodeValue());
    }
}
