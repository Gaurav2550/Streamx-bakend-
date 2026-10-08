CREATE TABLE videos (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        description VARCHAR(2000),
                        thumbnail_url VARCHAR(500),
                        video_url VARCHAR(500),
                        status VARCHAR(20) NOT NULL DEFAULT 'UPLOADING',
                        views BIGINT NOT NULL DEFAULT 0,
                        user_id BIGINT NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP NOT NULL,

                        CONSTRAINT fk_videos_user
                            FOREIGN KEY (user_id)
                                REFERENCES users(id)
                                ON DELETE CASCADE
);