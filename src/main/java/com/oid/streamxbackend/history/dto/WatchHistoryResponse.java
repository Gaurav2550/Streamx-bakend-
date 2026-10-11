package com.oid.streamxbackend.history.dto;

import java.time.LocalDateTime;

public record WatchHistoryResponse(
        Long id,
        Long videoId,
        String videoTitle,
        String thumbnailUrl,
        LocalDateTime watchedAt
) {
}
