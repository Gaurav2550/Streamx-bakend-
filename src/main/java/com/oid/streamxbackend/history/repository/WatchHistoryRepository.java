 package com.oid.streamxbackend.history.repository;

import com.oid.streamxbackend.history.entity.WatchHistory;
import com.oid.streamxbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

 public interface WatchHistoryRepository
        extends JpaRepository<WatchHistory, Long> {

    Page<WatchHistory> findByUser_IdOrderByWatchedAtDesc(
            Long userId,
            Pageable pageable
    );

    void deleteByUserAndVideo_Id(User user, Long videoId);

    Optional<WatchHistory> findByUser_IdAndVideo_Id(
            Long userId,
            Long videoId
    );
}