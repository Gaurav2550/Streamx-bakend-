package com.oid.streamxbackend.video.repository;

import com.oid.streamxbackend.video.entity.Video;
import com.oid.streamxbackend.video.entity.VideoStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long > {
    Page<Video> findByStatus(VideoStatus status , Pageable pageable);

    @Query("""
         SELECT v FROM Video v
        WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(v.description) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    Page<Video>searchVideos(@Param("search") String search , Pageable pageable);
}
