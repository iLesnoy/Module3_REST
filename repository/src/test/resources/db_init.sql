CREATE TABLE gift_certificate (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(65) NOT NULL,
    `description` VARCHAR(45) NOT NULL,
    `price` BIGINT NOT NULL,
    `duration` INT NOT NULL,
    `create_date` DATETIME NOT NULL,
    `last_update_date` DATETIME NOT NULL);



-- -----------------------------------------------------
-- Table `gifts`.`users`
-- -----------------------------------------------------
CREATE TABLE users (
     `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     `name` VARCHAR(55) NOT NULL);

-- -----------------------------------------------------
-- Table `gifts`.`orders`
-- -----------------------------------------------------
CREATE TABLE orders (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `order_date` DATETIME NOT NULL,
    `order_cost` DECIMAL(7,2) NOT NULL,
    `users_id` BIGINT NOT NULL,
     FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE CASCADE);




-- -----------------------------------------------------
-- Table `gifts`.`gift_certificate_has_orders`
-- -----------------------------------------------------
CREATE TABLE gift_certificate_has_orders (
    `id` BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `gift_certificate_id` BIGINT NOT NULL,
    `orders_id` BIGINT NOT NULL);



-- -----------------------------------------------------
-- Table `gifts`.`tag`
-- -----------------------------------------------------
CREATE TABLE tag (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(65) NOT NULL,
    PRIMARY KEY (`id`));



-- -----------------------------------------------------
-- Table `gifts`.`gift_certificate_has_tag`
-- -----------------------------------------------------
CREATE TABLE gift_certificate_has_tag (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `gift_certificate_id` BIGINT NOT NULL,
    `tag_id` BIGINT NOT NULL,
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE);