package com.oid.streamxbackend.auth.service;

import com.oid.streamxbackend.auth.dto.AuthResponse;
import com.oid.streamxbackend.auth.dto.LoginRequest;
import com.oid.streamxbackend.auth.dto.RegisterRequest;
import com.oid.streamxbackend.security.JwtService;
import com.oid.streamxbackend.user.entity.Role;
import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    public AuthService(UserRepository userRepository , PasswordEncoder passwordEncoder , JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

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

    public AuthResponse login(LoginRequest request){
        User user  = userRepository.findByEmail(request.email())
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid  Email or Password")
                );

    if (!passwordEncoder.matches(
            request.password(),
            user.getPassword()
    )){
        throw  new IllegalArgumentException("Invalid email or password");
    }

    String token  =  jwtService.generateToken(user);
       return new AuthResponse(token , "Bearer");
    }



}
