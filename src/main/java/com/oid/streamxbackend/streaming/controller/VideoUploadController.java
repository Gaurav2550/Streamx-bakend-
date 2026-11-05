
package com.oid.streamxbackend.streaming.controller;

import com.oid.streamxbackend.streaming.service.VideoStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoUploadController {

    private final VideoStorageService videoStorageService;

    public VideoUploadController(
            VideoStorageService videoStorageService) {
        this.videoStorageService = videoStorageService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadVideo(
            @RequestParam("file") MultipartFile file) {

        String filename = videoStorageService.storeVideo(file);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Video uploaded successfully",
                        "filename", filename
                )
        );
    }
}
