package com.oid.streamxbackend.auth.service;

import com.oid.streamxbackend.auth.dto.RegisterRequest;
import com.oid.streamxbackend.user.entity.Role;
import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository , PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public void register(RegisterRequest request){

        if(userRepository.existsByEmail(request.email())){
            throw new IllegalArgumentException("Email is already exist");
        }

        if (userRepository.existsByUsername(request.username())){
            throw  new IllegalArgumentException("username is already exist");
        }

        User user  =  User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

    }



}
