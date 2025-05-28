package com.example.bankcards.controller;

import com.example.bankcards.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    public record AuthRequest(String username, String password) {}

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request){
        System.out.println("LOGIN: " + request.username() + " / " + request.password());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }

        UserDetails user = customUserDetailsService.loadUserByUsername(request.username());
        String token = jwtUtil.generateToken(user.getUsername(), user.getAuthorities().iterator().next().getAuthority());
        return Map.of("token", token);
    }

}
