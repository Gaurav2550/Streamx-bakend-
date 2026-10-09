package com.oid.streamxbackend.video.repository;

import com.oid.streamxbackend.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long > {
}
