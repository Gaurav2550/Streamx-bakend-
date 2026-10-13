package com.oid.streamxbackend.interaction.repository;

import com.oid.streamxbackend.interaction.entity.VideoReaction;
import com.oid.streamxbackend.interaction.entity.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoReactionRepository
        extends JpaRepository<VideoReaction, Long> {

    Optional<VideoReaction> findByUser_IdAndVideo_Id(
            Long userId,
            Long videoId
    );

    long countByVideo_IdAndReaction(
            Long videoId,
            ReactionType reaction
    );
}
