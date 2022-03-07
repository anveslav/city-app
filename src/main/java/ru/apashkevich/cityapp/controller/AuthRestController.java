package ru.apashkevich.cityapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.apashkevich.cityapp.dto.AuthResponse;
import ru.apashkevich.cityapp.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthRestController {

    private final UserService userService;

    @GetMapping(path = "/login")
    public ResponseEntity<AuthResponse> auth() {
        return ResponseEntity.ok(AuthResponse.builder()
                .message("SUCCESS")
                .roles(userService.getUserRoles())
                .build());
    }
}
