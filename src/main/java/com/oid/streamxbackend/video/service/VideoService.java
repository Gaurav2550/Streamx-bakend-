package com.oid.streamxbackend.video.service;

import com.oid.streamxbackend.common.exception.VideoAccessDeniedException;
import com.oid.streamxbackend.common.exception.VideoNotFoundException;
import com.oid.streamxbackend.user.entity.User;
import com.oid.streamxbackend.video.dto.CreateVideoRequest;
import com.oid.streamxbackend.video.dto.UpdateVideoRequest;
import com.oid.streamxbackend.video.dto.VideoResponse;
import com.oid.streamxbackend.video.entity.Video;
import com.oid.streamxbackend.video.entity.VideoStatus;
import com.oid.streamxbackend.video.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VideoService {


    private  final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository){
        this.videoRepository = videoRepository;
    }

    public VideoResponse createVideo(CreateVideoRequest request , User user){

        Video  video = Video.builder()
                .title(request.title())
                .description(request.description())
                .thumbnailUrl(request.thumbnailUrl())
                .videoUrl(request.videoUrl())
                .status(VideoStatus.UPLOADING)
                .views(0L)
                .uploadedBy(user)
                .build();


        Video saveVideo =   videoRepository.save(video);

        return toResponse(saveVideo);
    }


     public Page<VideoResponse> getAllVideos(VideoStatus status ,Pageable pageable){

         Page<Video> videos;

         if (status == null){
             videos = videoRepository.findAll(pageable);
         }else {
             videos =  videoRepository.findByStatus(status , pageable);
         }
          return videos.map(this::toResponse);
     }


     public  VideoResponse  getVideoById(Long id){
        Video video =  videoRepository.findById(id)
                .orElseThrow(()-> new VideoNotFoundException("Video not found with " + id));
      return  toResponse(video);
    }



    public  VideoResponse updateVideo(Long id , UpdateVideoRequest request , User user){
        Video video = videoRepository.findById(id)
                .orElseThrow(
                        () -> new VideoNotFoundException("Video not found with " + id)
                );

         validOwnerShip(video,user);

         video.setTitle(request.title());
         video.setDescription(request.description());
         video.setThumbnailUrl(request.thumbnailUrl());
         video.setVideoUrl(request.videoUrl());

         Video updateVideo = videoRepository.save(video);

         return toResponse(updateVideo);
    }

    public  void  deleteVideo(Long id , User user){
         Video video =  videoRepository.findById(id)
                 .orElseThrow(()-> new VideoNotFoundException("\" Video not found with " + id));

         validOwnerShip(video,user);

         videoRepository.delete(video);
    }


    private  void validOwnerShip(Video
                                 video , User user){
        if(!video.getUploadedBy().getId().equals(user.getId())){
            throw new VideoAccessDeniedException(
                    "You not allowed  to modify this video"
            );
        }
    }



    private  VideoResponse toResponse(Video  video){
        return new VideoResponse(
                video.getId(),
                video.getTitle(),
                video.getDescription(),
                video.getThumbnailUrl(),
                video.getVideoUrl(),
                video.getStatus(),
                video.getViews(),
                video.getUploadedBy().getId(),
                video.getCreatedAt(),
                video.getUpdatedAt()
        );
    }





}
