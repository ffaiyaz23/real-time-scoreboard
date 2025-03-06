package com.example.user_service.service;

import com.example.user_service.dto.UserRegistrationRequest;
import com.example.user_service.dto.UserLoginRequest;
import com.example.user_service.model.User;
import com.example.user_service.repo.UserRepo;
import com.example.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(UserRegistrationRequest request) throws Exception {
        // Check if a user with the given username or email already exists.
        Optional<User> existingByUsername = userRepo.findByUsername(request.getUsername());
        if(existingByUsername.isPresent()){
            throw new Exception("User already registered with username: " + request.getUsername());
        }
        Optional<User> existingByEmail = userRepo.findByEmail(request.getEmail());
        if(existingByEmail.isPresent()){
            throw new Exception("User already registered with email: " + request.getEmail());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepo.save(user);
    }

    public String authenticateUser(UserLoginRequest request) throws Exception {
        Optional<User> userOpt = userRepo.findByUsername(request.getUsername());
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
                // Generate a JWT token using the username as the subject.
                return jwtUtil.generateToken(user.getUsername());
            } else {
                throw new Exception("Invalid credentials");
            }
        } else {
            throw new Exception("User not found");
        }
    }
}
