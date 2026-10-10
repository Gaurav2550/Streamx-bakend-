package com.oid.streamxbackend.video.repository;

import com.oid.streamxbackend.video.entity.Video;
import com.oid.streamxbackend.video.entity.VideoStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


        @Query("""
           SELECT DISTINCT v FROM Video v
           JOIN v.categories c
           WHERE c.id = :categoryId
""")
    Page<Video> findByCategoryId(@Param("categoryId") Long categoryId , Pageable pageable);


    @Query("""
    SELECT DISTINCT v FROM Video v
    LEFT JOIN v.categories c
    WHERE (:search IS NULL OR
           LOWER(v.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
           LOWER(COALESCE(v.description, '')) LIKE
               LOWER(CONCAT('%', :search, '%')))
      AND (:status IS NULL OR v.status = :status)
      AND (:categoryId IS NULL OR c.id = :categoryId)
    """)
    Page<Video> findVideosWithFilters(
            @Param("search") String search,
            @Param("status") VideoStatus status,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    @Modifying
    @Query("""
    UPDATE Video v
    SET v.views = v.views + 1
    WHERE v.id = :videoId
    """)
    int incrementViews(@Param("videoId") Long videoId);

}
