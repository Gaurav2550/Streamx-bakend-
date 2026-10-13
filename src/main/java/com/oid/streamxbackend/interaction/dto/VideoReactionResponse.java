package com.oid.streamxbackend.interaction.dto;

public record VideoReactionResponse(
        Long videoId,
        String reaction,
        long likes,
        long dislikes
) {}
