package com.oid.streamxbackend.category.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false , unique = true , length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected  void   onCreate(){
        createdAt  =  LocalDateTime.now();
    }

}
