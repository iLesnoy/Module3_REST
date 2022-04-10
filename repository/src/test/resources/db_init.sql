create table tag
(
    id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(75) NOT NULL UNIQUE
);

create table gift_certificate
(
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(65)  NOT NULL,
    description      VARCHAR(45) NOT NULL,
    price            DECIMAL(5,2) UNSIGNED NOT NULL,
    create_date      TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP NOT NULL,
    duration         TINYINT UNSIGNED NOT NULL
);

create table gift_certificate_has_tag
(
    gift_certificate_id BIGINT UNSIGNED NOT NULL,
    tag_id              BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (ID) ON DELETE CASCADE
);