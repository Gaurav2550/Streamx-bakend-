
CREATE TABLE watch_history (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               video_id BIGINT NOT NULL,
                               watched_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                               CONSTRAINT fk_watch_history_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE,

                               CONSTRAINT fk_watch_history_video
                                   FOREIGN KEY (video_id)
                                       REFERENCES videos(id)
                                       ON DELETE CASCADE
);

CREATE INDEX idx_watch_history_user_watched_at
    ON watch_history(user_id, watched_at DESC);

CREATE INDEX idx_watch_history_video
    ON watch_history(video_id);
