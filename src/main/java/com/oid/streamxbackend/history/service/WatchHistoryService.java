package com.oid.streamxbackend.history.service;

import com.oid.streamxbackend.common.exception.InvalidCredentialException;
import com.oid.streamxbackend.common.exception.VideoNotFoundException;
import com.oid.streamxbackend.history.dto.WatchHistoryResponse;
import com.oid.streamxbackend.history.entity.WatchHistory;
import com.oid.streamxbackend.history.repository.WatchHistoryRepository;
import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.user.repository.UserRepository;
import com.oid.streamxbackend.video.entity.Video;
import com.oid.streamxbackend.video.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public class WatchHistoryService {
    private  final WatchHistoryRepository watchHistoryRepository ;
    private  final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public WatchHistoryService(WatchHistoryRepository watchHistoryRepository, UserRepository userRepository, VideoRepository videoRepository) {
        this.watchHistoryRepository = watchHistoryRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    public WatchHistoryResponse recordWatch(Long  userId ,  Long videoId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidCredentialException("User not found"));

        Video video =  videoRepository.findById(videoId)
                .orElseThrow(()-> new VideoNotFoundException("Video not found with Id: " + videoId ));

        WatchHistory history = watchHistoryRepository
                .findByUser_IdAndVideo_Id(userId, videoId)
                .orElseGet(() -> WatchHistory.builder()
                        .user(user)
                        .video(video)
                        .build());

        history.setWatchedAt(LocalDateTime.now());
       WatchHistory saved =   watchHistoryRepository.save(history);

         return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<WatchHistoryResponse> getWatchHistory(
            Long userId,
            Pageable pageable) {

        return watchHistoryRepository
                .findByUser_IdOrderByWatchedAtDesc(userId, pageable)
                .map(this::toResponse);
    }
    private  WatchHistoryResponse toResponse(WatchHistory history){
        Video video = history.getVideo();

        return  new WatchHistoryResponse(
                history.getId(),
                video.getId(),
                video.getTitle(),
                video.getThumbnailUrl(),
                history.getWatchedAt()

        );
    }


    @Transactional
    public void deleteWatchHistory(Long userId, Long historyId) {

        int deletedRows =
                watchHistoryRepository.deleteByHistoryIdAndUserId(
                        historyId, userId);

        if (deletedRows == 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Watch history entry not found"
            );
        }
    }


}
