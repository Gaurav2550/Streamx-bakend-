package com.oid.streamxbackend.auth.dto;

public record AuthResponse(
        String token,
        String tokenType
) {
}
