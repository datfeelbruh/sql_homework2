DROP TABLE IF EXISTS app_user, app_user_post, app_user_comment, app_user_like;

CREATE TABLE app_user (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    created_at timestamp
);

CREATE TABLE app_user_post (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    text varchar(255) NOT NULL,
    created_at timestamp,
    user_id bigint REFERENCES app_user(id) NOT NULL
);

CREATE TABLE app_user_comment (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    text varchar(255),
    post_id bigint REFERENCES app_user_post(id) NOT NULL,
    user_id bigint REFERENCES app_user(id) NOT NULL,
    created_at timestamp
);

CREATE TABLE app_user_like (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id bigint REFERENCES app_user(id) NOT NULL,
    post_id bigint REFERENCES app_user_post(id),
    comment_id bigint REFERENCES app_user_comment(id)
    CHECK(post_id IS NOT NULL OR comment_id IS NOT NULL)
);
