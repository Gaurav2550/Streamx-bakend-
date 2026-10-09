package com.oid.streamxbackend.video.controller;


import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.video.service.VideoService;
import com.oid.streamxbackend.video.dto.CreateVideoRequest;
import com.oid.streamxbackend.video.dto.UpdateVideoRequest;
import com.oid.streamxbackend.video.dto.VideoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService){
        this.videoService=videoService;
    }

    public ResponseEntity<VideoResponse> createVideo(@Valid @RequestBody
                                                     CreateVideoRequest videoRequest
                                                     , Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();

        VideoResponse videoResponse  = videoService.createVideo(videoRequest,user);

        return ResponseEntity.status(HttpStatus.CREATED).body(videoResponse);

    }

    @GetMapping
    public ResponseEntity<List<VideoResponse>> getAllVideos(){
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<VideoResponse> getVideoById(@PathVariable Long id){
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable Long id  ,
                                                     @RequestBody UpdateVideoRequest request ,
                                                     Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(videoService.updateVideo(id,request ,user));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteVideo(@PathVariable Long id , Authentication authentication){
            User user = (User) authentication.getPrincipal();
            videoService.deleteVideo(id,user);
            return ResponseEntity.noContent().build();
    }
}
