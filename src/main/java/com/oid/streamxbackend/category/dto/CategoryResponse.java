package com.oid.streamxbackend.category.dto;

import java.time.LocalDateTime;

public record CategoryResponse (
        Long id,
        String  name,
        String description,
        LocalDateTime createdAt
){
}
