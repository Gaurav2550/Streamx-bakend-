package com.oid.streamxbackend.video.service;


import com.oid.streamxbackend.common.exception.VideoNotFoundException;
import com.oid.streamxbackend.video.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @Test
    void incrementViews_whenVideoExists_shouldSucceed() {
        Long videoId = 1L;

        when(videoRepository.incrementViews(videoId))
                .thenReturn(1);

        assertDoesNotThrow(
                () -> videoService.incrementViews(videoId)
        );

        verify(videoRepository).incrementViews(videoId);
    }

    @Test
    void incrementViews_whenVideoDoesNotExist_shouldThrow() {
        Long videoId = 999L;

        when(videoRepository.incrementViews(videoId))
                .thenReturn(0);

        assertThrows(
                VideoNotFoundException.class,
                () -> videoService.incrementViews(videoId)
        );

        verify(videoRepository).incrementViews(videoId);
    }
}
