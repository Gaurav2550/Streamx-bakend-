package com.oid.streamxbackend.interaction.controller;

import com.oid.streamxbackend.interaction.dto.VideoReactionResponse;
import com.oid.streamxbackend.interaction.entity.ReactionType;
import com.oid.streamxbackend.interaction.service.VideoReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/videos/{videoId}/reactions")

public class VideoReactionController {

    private final VideoReactionService reactionService;

    public VideoReactionController(VideoReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PutMapping
    public ResponseEntity<VideoReactionResponse> react(
            @PathVariable Long videoId,
            @RequestParam ReactionType type,
            Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());

        return ResponseEntity.ok(
                reactionService.react(userId, videoId, type)
        );
    }

    @DeleteMapping
    public ResponseEntity<VideoReactionResponse> removeReaction(
            @PathVariable Long videoId,
            Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());

        return ResponseEntity.ok(
                reactionService.removeReaction(userId, videoId)
        );
    }

    @GetMapping
    public ResponseEntity<VideoReactionResponse> getReactionCounts(
            @PathVariable Long videoId) {

        return ResponseEntity.ok(
                reactionService.getReactionCounts(videoId, null)
        );
    }
}