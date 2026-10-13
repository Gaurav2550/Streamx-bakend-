
CREATE TABLE video_reactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    video_id BIGINT NOT NULL,
    reaction VARCHAR(10) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_video_reactions_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_video_reactions_video
        FOREIGN KEY (video_id)
        REFERENCES videos(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_video_reactions_type
        CHECK (reaction IN ('LIKE', 'DISLIKE')),

    CONSTRAINT uq_video_reactions_user_video
        UNIQUE (user_id, video_id)
);

CREATE INDEX idx_video_reactions_video_id
    ON video_reactions(video_id);
