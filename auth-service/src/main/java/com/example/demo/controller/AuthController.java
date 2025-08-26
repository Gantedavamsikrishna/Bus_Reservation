package com.example.demo.controller;


import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    
    @PostMapping("/register")
    public String register(@RequestBody User user) {
    	 System.out.println("Register request: " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encrypt password
        User saved= userRepository.save(user);
        System.out.println("Saved user: " + saved);
        return "User registered successfully!";
    }

    
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return new AuthResponse(token);
            }
        }
        throw new RuntimeException("Invalid username or password");
    }
}
