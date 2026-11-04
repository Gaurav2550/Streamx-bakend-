
package com.oid.streamxbackend.streaming.service;

import com.oid.streamxbackend.streaming.config.VideoStorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@Service
public class VideoStorageService {

    private final Path storageLocation;

    public VideoStorageService(VideoStorageConfig config) {
        this.storageLocation = config.videoStorageLocation();

        try {
            Files.createDirectories(storageLocation);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not initialize video storage", e
            );
        }
    }

    public String storeVideo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Video file is empty");
        }
        if (file.getSize() > 500L * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "Video file must not exceed 500 MB"
            );
        }

        validateVideoFile(file);
        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null &&
                originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            ).toLowerCase();
        }

        String filename = UUID.randomUUID() + extension;

        Path destination = storageLocation
                .resolve(filename)
                .normalize();

        try {
            Files.copy(file.getInputStream(), destination);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not store video file", e
            );
        }
    }

    public Path loadVideo(String filename) {
        Path file = storageLocation.resolve(filename).normalize();

        if (!file.startsWith(storageLocation)) {
            throw new IllegalArgumentException("Invalid filename");
        }

        return file;
    }

    private void validateVideoFile(MultipartFile file) {

        String contentType = file.getContentType();

        Set<String> allowedTypes = Set.of(
                "video/mp4",
                "video/webm",
                "video/quicktime",
                "video/x-matroska"
        );

        if (contentType == null ||
                !allowedTypes.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException(
                    "Unsupported video format"
            );
        }
    }

}
