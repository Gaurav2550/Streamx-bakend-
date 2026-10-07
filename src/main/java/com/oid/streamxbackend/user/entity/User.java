package com.oid.streamxbackend.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;

    @Column(nullable = false, unique = true , length = 50)
    private String username;

    @Column(nullable = false , unique = true , length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    @Builder.Default
    private Role role = Role.USER;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private  LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate(){
        LocalDateTime localDateTime = LocalDateTime.now();
        createdAt = localDateTime;
        updatedAt  = localDateTime ;
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
