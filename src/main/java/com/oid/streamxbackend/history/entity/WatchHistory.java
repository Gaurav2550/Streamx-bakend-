package com.oid.streamxbackend.history.entity;

import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.video.entity.Video;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "watch_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id" , nullable = false )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "video_id" , nullable = false )
    private Video video;

    @Column(name = "watched_at" , nullable = false)
    private LocalDateTime watchedAt;

    @PrePersist
    protected  void onCreate(){
        if (watchedAt == null){
            watchedAt = LocalDateTime.now();
        }
    }


}
