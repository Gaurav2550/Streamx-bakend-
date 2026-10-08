package com.oid.streamxbackend.video.repository;

import com.oid.streamxbackend.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long > {
}
