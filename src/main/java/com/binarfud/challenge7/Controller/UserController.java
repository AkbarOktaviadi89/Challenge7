package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Model.Users;
import com.binarfud.challenge7.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Transactional(readOnly = true)
    @Operation(summary = "Get All Users")
    @GetMapping("/get-users")
    public ResponseEntity<List<Users>> getAllUser(@RequestParam(required = false) String username) {
        try {
            List<Users> users = new ArrayList<>();

            if (username == null) {
                userService.findAllUsers().forEach(users::add);
            } else {
                Optional<Users> user = userService.findByUsername(username);
                if (user.isPresent()) {
                    users.add(user.get());
                }
            }

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    @Operation(summary = "Get User By Id")
    @GetMapping("/get-users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") long id) {
        try {
            Optional<Users> userData = userService.findById(id);
            if (userData.isPresent()) {
                return new ResponseEntity<>(userData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create New Users")
    @PostMapping("/create-users")
    public ResponseEntity<Users> creteUser(@RequestBody Users user) {
        try {
            Users _user = new Users(user.getUsername(), user.getEmailAddress(), user.getPassword());
            if (userService.addNewUser(_user)) {
                return new ResponseEntity<>(_user, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_CUSTOMER")
    @Operation(summary = "Edit Users By Id")
    @PutMapping("/edit-users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable("id") long id, @RequestBody Users user) {
        Optional<Users> userData = userService.findById(id);
        try {
            if (userData.isPresent()) {
                Users _user = userData.get();
                _user.setUsername(user.getUsername());
                _user.setPassword(user.getPassword());
                _user.setEmailAddress(user.getEmailAddress());
                if (userService.updateUserbyId(_user)) {
                    return new ResponseEntity<>(_user, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_CUSTOMER")
    @Operation(summary = "Delete Users By Id")
    @DeleteMapping("/delete-users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id) {
        try {
            if (userService.deleteAllUser()) {
                return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_CUSTOMER")
    @Operation(summary = "Delete All Users")
    @DeleteMapping("/delete-users")
    public ResponseEntity deleteAllUser() {
        try {
            if (userService.deleteAllUser()) {
                return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
