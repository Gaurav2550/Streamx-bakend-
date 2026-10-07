package com.oid.streamxbackend.user.controller;

import com.oid.streamxbackend.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String , Object>> getCurrentUser(
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                Map.of(
              "id", user.getId(),
               "username", user.getUsername(),
                "email" , user.getEmail(),
                 "role" , user.getRole().name()
                )
        );

    }
}
