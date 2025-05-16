package com.workout.heavylift.controller;


import com.workout.heavylift.dto.user.CreateUserRequest;
import com.workout.heavylift.dto.user.UserResponse;
import com.workout.heavylift.service.AuthService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam @Email String email,
                                        @RequestParam @NotBlank String password) {
        String token = authService.login(email, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String oldToken) {
        String token = oldToken.replace("Bearer ", "");
        return ResponseEntity.ok(authService.refreshToken(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String rawToken = token.replace("Bearer ", "");
        authService.logout(rawToken);
        return ResponseEntity.ok().build();
    }
}
