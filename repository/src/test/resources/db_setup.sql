INSERT INTO gift_certificate (name, description, price,duration, create_date, last_update_date)
VALUES ('certificate1', 'description1', 100, 1,'2022-10-10 11:11:11', '2022-04-10 11:11:11'),
       ('certificate2', 'description2', 500, 2,'2022-12-10', '2022-04-10 10:22:22'),
       ('certificate3', 'description3', 200, 3,'2022-02-10 12:33:33', '2022-03-10 12:22:33');

INSERT INTO tag (name) VALUES ('EPAM'), ('REST'), ('MAVEN'), ('JAVA');

INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 1), (1, 3), (2, 2);

INSERT INTO users (id,name) VALUES (1,'Egor'), (2,'Ann'), (3,'Vaz');

/*INSERT INTO orders (order_date,order_cost,user_id) VALUES (2022-02-10,200,1),(2020-04-15,500,2);*/