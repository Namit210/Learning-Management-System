package com.amit.controller;

import com.amit.config.JwtUtil;
import com.amit.dto.AuthRequest;
import com.amit.dto.AuthResponse;
import com.amit.model.User;
import com.amit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Value("${app.admin.secret.key}")
	private String adminSecretKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
    	try {
            // Check if user already exists
            if (userRepository.findById(user.getNickname()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(null, null, "User already exists"));
            }

            // Encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Determine roles based on admin secret key
            Set<String> roles = new HashSet<>();
            
            // Check if admin secret key is provided and valid
            if (user.getAdminSecretKey() != null && !user.getAdminSecretKey().isEmpty()) {
                if (user.getAdminSecretKey().equals(adminSecretKey)) {
                    // Valid admin key - grant ADMIN roles
                    roles.add("ADMIN");
                    System.out.println("Admin user registered: " + user.getNickname());
                } else {
                    // Invalid admin key - reject registration
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(new AuthResponse(null, null, "Invalid admin secret key"));
                }
            } else {
                // No admin key provided - regular user registration
                roles.add("USER");
            }
            
            user.setRoles(roles);
            user.setAdminSecretKey(null);  // Clear the secret key before saving (security measure)

            // Save user
            User savedUser = userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(null, savedUser.getNickname(), "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, null, "Error: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getNickname(),
                            authRequest.getPassword()
                    )
            );

            // Load user details
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getNickname());

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(jwt, authRequest.getNickname(), "Login successful"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, null, "Error: " + e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String username = jwtUtil.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(token, userDetails)) {
                    return ResponseEntity.ok(new AuthResponse(null, username, "Token is valid"));
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "Invalid token"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "Token validation failed"));
        }
    }
}
