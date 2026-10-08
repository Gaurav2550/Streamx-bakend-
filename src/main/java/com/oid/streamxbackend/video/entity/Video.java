package com.oid.streamxbackend.video.entity;

import com.oid.streamxbackend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;


@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 200)
    private  String title;

    @Column(length = 2000)
    private String description;

    @Column(name = "thumbnail_url" , length = 500)
    private String thumbnailUrl;

    @Column(name = "video_url" , length = 500)
    private String videoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false , length = 20)
    @Builder.Default
    private  VideoStatus videoStatus  =  VideoStatus.UPLOADING;

    @Column(nullable = false)
    @Builder.Default
    private  Long views = 0L ;

    @ManyToOne(fetch = FetchType.LAZY , optional = false )
    @JoinColumn(name = "user_id" , nullable = false)
    private User uploadedBy;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private  LocalDateTime updatedAt;



    @PrePersist
    protected  void onCreate(){
        LocalDateTime now =  LocalDateTime.now();
        createdAt =  now;
        updatedAt = now;
    }

    @PreUpdate
    protected  void onUpdate(){
        updatedAt = LocalDateTime.now();
    }




}
