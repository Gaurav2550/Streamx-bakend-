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

        WatchHistory watchHistory = WatchHistory.builder()
                .user(user)
                .video(video)
                .build();
       WatchHistory saved =   watchHistoryRepository.save(watchHistory);

         return toResponse(saved);
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
}
