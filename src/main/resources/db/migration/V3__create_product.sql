CREATE TABLE product
(
    id          BINARY(16) PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    unit_price  DECIMAL(10, 2) NOT NULL,
    category_id BINARY(16)     NOT NULL,
    created_at  DATETIME       NOT NULL,
    created_by  BINARY(16)     NOT NULL,
    updated_at  DATETIME,
    updated_by  BINARY(16),
    deleted     BOOLEAN        NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
            REFERENCES category (id)
);