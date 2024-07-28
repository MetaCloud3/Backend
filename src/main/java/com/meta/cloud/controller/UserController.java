package com.meta.cloud.controller;

import com.meta.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok(userService.login("admin", "admin"));
    }

    @PostMapping("/info")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("user Info");
    }
}
