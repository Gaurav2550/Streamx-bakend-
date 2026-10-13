
package com.oid.streamxbackend.history.controller;

import com.oid.streamxbackend.history.dto.WatchHistoryResponse;
import com.oid.streamxbackend.history.service.WatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/history")
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    public WatchHistoryController(WatchHistoryService watchHistoryService) {
        this.watchHistoryService = watchHistoryService;
    }

    @PostMapping("/videos/{videoId}")
    public ResponseEntity<WatchHistoryResponse> recordWatch(
            @PathVariable Long videoId,
            Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());

        WatchHistoryResponse response =
                watchHistoryService.recordWatch(userId, videoId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<WatchHistoryResponse>> getWatchHistory(
            Authentication authentication,
            @PageableDefault(
                    size = 10,
                    sort = "watchedAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        Long userId = Long.valueOf(authentication.getName());

        return ResponseEntity.ok(
                watchHistoryService.getWatchHistory(userId, pageable)
        );
    }
    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> deleteWatchHistory(
            @PathVariable Long historyId,
            Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());

        watchHistoryService.deleteWatchHistory(userId, historyId);

        return ResponseEntity.noContent().build();
    }
}