INSERT INTO category(id, category_name) VALUES(1, 'горячее');
INSERT INTO category(id, category_name) VALUES(2, 'кофе');
INSERT INTO category(id, category_name) VALUES(3, 'холодное');
INSERT INTO drink(id, name, price, volume, description) VALUES(1, 'эспрессо', 100, 50, 'супер кофе');
INSERT INTO drink(id, name, price, volume, description) VALUES(2, 'молоко', 50, 250, 'супер молоко');
INSERT INTO drink_img(image, drink_id) VALUES ('espresso', 1);
INSERT INTO drink_img(image, drink_id) VALUES ('milk', 2);
INSERT INTO drink_category(drink_id, category_id) VALUES(1, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(1, 2);
INSERT INTO drink_category(drink_id, category_id) VALUES(2, 3);
INSERT INTO topping(name, price) VALUES('сахар', 0);
INSERT INTO topping(name, price) VALUES('сливки', 15);
INSERT INTO topping(name, price) VALUES('мармеладные мишки', 20);
