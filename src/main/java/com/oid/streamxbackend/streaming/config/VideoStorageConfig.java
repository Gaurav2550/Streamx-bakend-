
package com.oid.streamxbackend.streaming.config;

import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class VideoStorageConfig {

    public Path videoStorageLocation() {
        return Paths.get("uploads", "videos")
                .toAbsolutePath()
                .normalize();
    }
}
