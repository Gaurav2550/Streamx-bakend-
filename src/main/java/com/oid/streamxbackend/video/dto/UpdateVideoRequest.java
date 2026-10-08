package com.oid.streamxbackend.video.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateVideoRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200 ,  message = "title must not exceed 200 characters")
        String title,

        @Size(max = 2000 , message = "description  must not be exceed 2000 characters")
        String description,


        @Size(max = 500 , message = "thumbnail url must not be exceed 500 characters")
        String thumbnailUrl
        ,
        @Size(max = 500 , message = "video  url must not be exceed 500 characters")
        String videoUrl

) {
}
