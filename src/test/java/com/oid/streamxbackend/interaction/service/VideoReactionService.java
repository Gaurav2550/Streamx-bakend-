package com.oid.streamxbackend.interaction.service;

import com.oid.streamxbackend.interaction.entity.ReactionType;
import com.oid.streamxbackend.interaction.entity.VideoReaction;
import com.oid.streamxbackend.interaction.repository.VideoReactionRepository;
import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.user.repository.UserRepository;
import com.oid.streamxbackend.video.entity.Video;
import com.oid.streamxbackend.common.exception.VideoNotFoundException;
import com.oid.streamxbackend.video.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoReactionServiceTest {

    @Mock
    private VideoReactionRepository reactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoReactionService reactionService;

    private User user;
    private Video video;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .password("encoded-password")
                .build();

        video = Video.builder()
                .id(10L)
                .title("Test Video")
                .build();
    }

    @Test
    void shouldCreateLikeWhenNoPreviousReactionExists() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(videoRepository.findById(10L))
                .thenReturn(Optional.of(video));
        when(reactionRepository.findByUser_IdAndVideo_Id(1L, 10L))
                .thenReturn(Optional.empty());
        when(reactionRepository.save(any(VideoReaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(videoRepository.existsById(10L)).thenReturn(true);
        when(reactionRepository.countByVideo_IdAndReaction(
                10L, ReactionType.LIKE)).thenReturn(1L);
        when(reactionRepository.countByVideo_IdAndReaction(
                10L, ReactionType.DISLIKE)).thenReturn(0L);

        var response = reactionService.react(1L, 10L, ReactionType.LIKE);

        assertEquals(10L, response.videoId());
        assertEquals("LIKE", response.reaction());
        assertEquals(1L, response.likes());
        assertEquals(0L, response.dislikes());

        verify(reactionRepository).save(any(VideoReaction.class));
    }

    @Test
    void shouldSwitchExistingReactionToDislike() {
        VideoReaction existing = VideoReaction.builder()
                .id(5L)
                .user(user)
                .video(video)
                .reaction(ReactionType.LIKE)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(videoRepository.findById(10L))
                .thenReturn(Optional.of(video));
        when(reactionRepository.findByUser_IdAndVideo_Id(1L, 10L))
                .thenReturn(Optional.of(existing));
        when(reactionRepository.save(any(VideoReaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(videoRepository.existsById(10L)).thenReturn(true);
        when(reactionRepository.countByVideo_IdAndReaction(
                10L, ReactionType.LIKE)).thenReturn(0L);
        when(reactionRepository.countByVideo_IdAndReaction(
                10L, ReactionType.DISLIKE)).thenReturn(1L);

        var response = reactionService.react(1L, 10L, ReactionType.DISLIKE);

        assertEquals(ReactionType.DISLIKE, existing.getReaction());
        assertEquals("DISLIKE", response.reaction());
        assertEquals(0L, response.likes());
        assertEquals(1L, response.dislikes());
    }

    @Test
    void shouldThrowWhenVideoDoesNotExist() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(videoRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                VideoNotFoundException.class,
                () -> reactionService.react(1L, 999L, ReactionType.LIKE)
        );

        verify(reactionRepository, never()).save(any());
    }
}
