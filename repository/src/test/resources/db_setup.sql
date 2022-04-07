INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration)
VALUES ('certificate1', 'description1', 1.1, '2022-10-10 11:11:11', '2022-04-10 11:11:11', 1),
       ('certificate2', 'description2', 2.2, '2022-12-10 12:22:22', '2022-04-10 10:22:22', 2),
       ('certificate3', 'description3', 3.3, '2022-02-10 12:33:33', '2022-03-10 12:22:33', 3);

INSERT INTO tag ("NAME") VALUES ('EPAM'), ('REST'), ('MAVEN'), ('JAVA');

INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 1), (1, 3), (2, 2);