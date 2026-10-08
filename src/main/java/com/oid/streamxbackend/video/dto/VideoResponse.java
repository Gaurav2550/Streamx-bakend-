package com.oid.streamxbackend.video.dto;

import com.oid.streamxbackend.video.entity.VideoStatus;

import java.time.LocalDateTime;

public record VideoResponse(
        Long id,
        String title,
        String description,
        String thumbnailUrl,
        String videoUrl,
        VideoStatus videoStatus,
        Long views,
        Long uploadedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
