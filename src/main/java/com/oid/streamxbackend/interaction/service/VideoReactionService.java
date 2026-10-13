package com.oid.streamxbackend.interaction.service;

import com.oid.streamxbackend.common.exception.VideoNotFoundException;
import com.oid.streamxbackend.interaction.dto.VideoReactionResponse;
import com.oid.streamxbackend.interaction.entity.ReactionType;
import com.oid.streamxbackend.interaction.entity.VideoReaction;
import com.oid.streamxbackend.interaction.repository.VideoReactionRepository;
import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.user.repository.UserRepository;
import com.oid.streamxbackend.video.entity.Video;
import com.oid.streamxbackend.video.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoReactionService {

    private final VideoReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public VideoReactionService(VideoReactionRepository reactionRepository, UserRepository userRepository, VideoRepository videoRepository) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    @Transactional
    public VideoReactionResponse react(
            Long userId,
            Long videoId,
            ReactionType reactionType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() ->
                        new VideoNotFoundException(
                                "Video not found with id: " + videoId));

        VideoReaction reaction = reactionRepository
                .findByUser_IdAndVideo_Id(userId, videoId)
                .orElseGet(() -> VideoReaction.builder()
                        .user(user)
                        .video(video)
                        .build());

        reaction.setReaction(reactionType);
        reactionRepository.save(reaction);

        return getReactionCounts(videoId, reactionType);
    }

    @Transactional
    public VideoReactionResponse removeReaction(
            Long userId,
            Long videoId) {

        reactionRepository.findByUser_IdAndVideo_Id(userId, videoId)
                .ifPresent(reactionRepository::delete);

        return getReactionCounts(videoId, null);
    }

    @Transactional(readOnly = true)
    public VideoReactionResponse getReactionCounts(
            Long videoId,
            ReactionType currentReaction) {

        if (!videoRepository.existsById(videoId)) {
            throw new VideoNotFoundException(
                    "Video not found with id: " + videoId);
        }

        long likes = reactionRepository.countByVideo_IdAndReaction(
                videoId, ReactionType.LIKE);

        long dislikes = reactionRepository.countByVideo_IdAndReaction(
                videoId, ReactionType.DISLIKE);

        return new VideoReactionResponse(
                videoId,
                currentReaction == null ? null : currentReaction.name(),
                likes,
                dislikes
        );
    }
}
