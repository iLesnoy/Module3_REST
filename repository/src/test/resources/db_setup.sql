INSERT INTO gift_certificate (id,name, description, price,duration, create_date, last_update_date)
VALUES (1,'certificate1', 'description1', 100, 1,'2022-10-10 11:11:11', '2022-04-10 11:11:11'),
       (2,'certificate2', 'description2', 500, 2,'2022-12-10', '2022-04-10 10:22:22'),
       (3,'certificate3', 'description3', 200, 3,'2022-02-10 12:33:33', '2022-03-10 12:22:33');

INSERT INTO tag (name) VALUES ('EPAM'), ('REST'), ('MAVEN'), ('JAVA');

INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 1), (1, 3), (2, 2);

INSERT INTO users (id,name) VALUES (1,'Egor'), (2,'Ann'), (3,'Vaz');

INSERT INTO orders (id,order_date,order_cost,users_id) VALUES (1,'2022-02-10 12:33:33',200,1),(2,'2022-02-10 12:33:33',500,2);

INSERT INTO gift_certificate_has_orders(gift_certificate_id,orders_id) VALUES (1,1),(2,2);
