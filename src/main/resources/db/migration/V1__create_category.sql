CREATE TABLE category
(
    id         BINARY(16) PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME     NOT NULL,
    created_by BINARY(16)   NOT NULL,
    updated_at DATETIME,
    updated_by BINARY(16),
    deleted    BOOLEAN      NOT NULL DEFAULT FALSE
);