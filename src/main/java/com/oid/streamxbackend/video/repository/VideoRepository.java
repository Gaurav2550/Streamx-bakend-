package com.oid.streamxbackend.video.repository;

import com.oid.streamxbackend.video.entity.Video;
import com.oid.streamxbackend.video.entity.VideoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long > {
    Page<Video> findByStatus(VideoStatus status , Pageable pageable);
}
