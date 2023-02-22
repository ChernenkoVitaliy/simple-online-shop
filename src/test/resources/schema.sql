CREATE TABLE account(
    id             BIGSERIAL PRIMARY KEY,
    email          VARCHAR(128) UNIQUE NOT NULL,
    password       VARCHAR(50)         NOT NULL,
    created_at     TIMESTAMP           NOT NULL,
    account_status VARCHAR(50)         NOT NULL
);

CREATE TABLE address(
    id      BIGSERIAL PRIMARY KEY,
    country VARCHAR(128) NOT NULL,
    city    VARCHAR(128) NOT NULL,
    street  VARCHAR(128) NOT NULL
);

CREATE TABLE seller(
    id                  BIGSERIAL PRIMARY KEY,
    account_id          BIGINT       NOT NULL,
    company_name        VARCHAR(128) NOT NULL,
    company_description TEXT         NOT NULL,
    company_site        VARCHAR(128),
    CONSTRAINT fk_seller_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE seller_phones(
    id        BIGSERIAL PRIMARY KEY,
    phone     VARCHAR(11) UNIQUE NOT NULL,
    seller_id BIGINT             NOT NULL,
    CONSTRAINT fk_seller_phones_seller_id
        FOREIGN KEY (seller_id)
            REFERENCES seller (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE customer(
    id         BIGSERIAL PRIMARY KEY,
    account_id BIGINT              NOT NULL,
    name       VARCHAR(128)        NOT NULL,
    surname    VARCHAR(128)        NOT NULL,
    phone      VARCHAR(128) UNIQUE NOT NULL,
    address_id BIGINT,
    CONSTRAINT fk_customer_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_customer_address_id
        FOREIGN KEY (address_id)
            REFERENCES address (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);

CREATE TABLE tag(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(128) UNIQUE NOT NULL,
    description VARCHAR(128)        NOT NULL
);

CREATE TABLE product(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(256)  NOT NULL,
    description TEXT          NOT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    seller_id   BIGINT        NOT NULL,
    CONSTRAINT fk_product_seller_id
        FOREIGN KEY (seller_id)
            REFERENCES seller (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE feedback(
    id            BIGSERIAL PRIMARY KEY,
    account_id    BIGINT    NOT NULL,
    feedback_text TEXT      NOT NULL,
    created_at    TIMESTAMP NOT NULL,
    product_id    BIGINT    NOT NULL,
    CONSTRAINT fk_feedback_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT fk_feedback_product_id
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE cart(
    id          BIGSERIAL PRIMARY KEY,
    customer_id BIGINT    NOT NULL,
    CONSTRAINT fk_cart_customer_id
        FOREIGN KEY (customer_id)
            REFERENCES customer (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE orders(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP    NOT NULL,
    order_status VARCHAR(128) NOT NULL,
    customer_id  BIGINT       NOT NULL,
    CONSTRAINT fk_order_customer_id
        FOREIGN KEY (customer_id)
            REFERENCES customer (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE delivery(
    id            BIGSERIAL PRIMARY KEY,
    order_id      BIGINT    NOT NULL,
    customer_id   BIGINT    NOT NULL,
    created_at    TIMESTAMP NOT NULL,
    delivery_date TIMESTAMP NULL,
    delivered_at  TIMESTAMP NULL,
    CONSTRAINT fk_delivery_order_id
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_delivery_customer_id
        FOREIGN KEY (customer_id)
            REFERENCES customer (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE orders_products(
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_orders_products_order_id
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_orders_products_product_id
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE wish_list_product_customer(
    product_id  BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, customer_id),
    CONSTRAINT fk_wish_list_product_customer_product_id
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_wish_list_product_customer_customer_id
        FOREIGN KEY (customer_id)
            REFERENCES customer (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE products_tags(
    product_id BIGINT NOT NULL,
    tag_id     BIGINT NOT NULL,
    PRIMARY KEY (product_id, tag_id),
    CONSTRAINT fk_products_tags_product_id
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_products_tags_tag_id
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE carts_products(
    cart_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (cart_id, product_id),
    CONSTRAINT fk_carts_products_cart_id
        FOREIGN KEY (cart_id)
            REFERENCES cart (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_carts_products_product_id
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);