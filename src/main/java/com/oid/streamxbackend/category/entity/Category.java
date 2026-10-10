package com.oid.streamxbackend.category.entity;

import com.oid.streamxbackend.video.entity.Video;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "categories")
    @Builder.Default
    private Set<Video> videos = new HashSet<>();

    @PrePersist
    protected  void   onCreate(){
        createdAt  =  LocalDateTime.now();
    }

}
