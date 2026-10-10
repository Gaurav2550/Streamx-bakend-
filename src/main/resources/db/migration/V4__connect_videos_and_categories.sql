
CREATE TABLE video_categories (
                                  video_id BIGINT NOT NULL,
                                  category_id BIGINT NOT NULL,

                                  PRIMARY KEY (video_id, category_id),

                                  CONSTRAINT fk_video_categories_video
                                      FOREIGN KEY (video_id)
                                          REFERENCES videos(id)
                                          ON DELETE CASCADE,

                                  CONSTRAINT fk_video_categories_category
                                      FOREIGN KEY (category_id)
                                          REFERENCES categories(id)
                                          ON DELETE CASCADE
);

CREATE INDEX idx_video_categories_category_id
    ON video_categories(category_id);
