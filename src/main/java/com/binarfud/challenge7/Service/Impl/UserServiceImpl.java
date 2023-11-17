package com.binarfud.challenge7.Service.Impl;

import com.binarfud.challenge7.Model.Users;
import com.binarfud.challenge7.Repository.UserRepository;
import com.binarfud.challenge7.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Users> findAllUsers() {
        try {
            log.info("Retrieving all available users.");
            return userRepository.getAllUsersAvailable();
        } catch (Exception e) {
            log.error("Error while retrieving all users: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve users.", e);
        }
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        try {
            log.info("Retrieving users by username: {}", username);
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error("Error while retrieving users by username: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve users by username.", e);
        }
    }

    @Override
    public Optional<Users> findById(Long id) {
        try {
            log.info("Retrieving user by ID: {}", id);
            return userRepository.findById(id);
        } catch (Exception e) {
            log.error("Error while retrieving user by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve user by ID.", e);
        }
    }

    @Override
    public Boolean addNewUser(Users user) {
        try {
            log.info("Adding a new user.");
            Users newUser = userRepository.save(user);
            if (newUser != null) {
                return true;
            } else {
                log.error("Failed adding a new User");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while adding a new user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add a new user.", e);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        try {
            log.info("Deleting user with ID: {}.", userId);
            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("Error while deleting user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete user.", e);
        }
    }

    @Override
    public boolean deleteAllUser() {
        try {
            log.info("Deleting all users.");
            userRepository.deleteAll();
            return true;
        } catch (Exception e) {
            log.error("Error while deleting all users: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete all users.", e);
        }
    }


    @Override
    @Transactional
    public boolean updateUserbyId(Users user) {
        try {
            log.info("Updating user with ID: {}.", user.getUserId());
            Optional<Users> existingUser = userRepository.findById(user.getUserId());

            if (existingUser.isPresent()) {
                userRepository.save(user);
                return true;
            } else {
                log.error("User with ID {} not found. Update failed.", user.getUserId());
                throw new RuntimeException("User not found for update.");
            }

        } catch (Exception e) {
            log.error("Error while updating user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update user.", e);
        }
    }
}
