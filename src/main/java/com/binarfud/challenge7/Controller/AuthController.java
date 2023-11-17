package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Config.JwtUtils;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                    userDetails.getEmail(), roles));
        } catch (AuthenticationException e) {
            log.error("Error during user authentication: " + e.getMessage(), e);
            return ResponseEntity.badRequest().body(new JwtResponse("Authentication failed: " + e.getMessage()));
        }

    }



    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest signupRequest) {
        try {
            Boolean usernameExist = usersRepository.existsByUsername(signupRequest.getUsername());
            if (Boolean.TRUE.equals(usernameExist)) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

            Boolean emailExist = usersRepository.existsByEmailAddress(signupRequest.getEmail());
            if (Boolean.TRUE.equals(emailExist)) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: Email is already taken!"));
            }

            Users users = new Users(signupRequest.getUsername(), signupRequest.getEmail(),
                    passwordEncoder.encode(signupRequest.getPassword()));

            Set<String> strRoles = signupRequest.getRole();
            Set<Roles> roles = new HashSet<>();

            if (strRoles == null) {
                Roles role = roleRepository.findByRoleName(ERole.ROLE_CUSTOMER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                roles.add(role);
            } else {
                strRoles.forEach(role -> {
                    Roles roles1 = roleRepository.findByRoleName(ERole.valueOf(role))
                            .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
                    roles.add(roles1);
                });
            }
            users.setRoles(roles);
            userService.addNewUser(users);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        } catch (Exception e) {
            log.error("Error during user registration: " + e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
