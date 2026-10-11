
package com.oid.streamxbackend.history.controller;

import com.oid.streamxbackend.history.dto.WatchHistoryResponse;
import com.oid.streamxbackend.history.service.WatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

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
}
```