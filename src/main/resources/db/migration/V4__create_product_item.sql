CREATE TABLE product_item
(
    id         CHAR(36) PRIMARY KEY,
    product_id CHAR(36) NOT NULL,
    item_id    CHAR(36) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by CHAR(36) NOT NULL,
    updated_at DATETIME,
    updated_by CHAR(36),
    deleted    BOOLEAN  NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_product_item_product
        FOREIGN KEY (product_id)
            REFERENCES product (id),

    CONSTRAINT fk_product_item_item
        FOREIGN KEY (item_id)
            REFERENCES item (id),

    CONSTRAINT uk_product_item UNIQUE (product_id, item_id)
);