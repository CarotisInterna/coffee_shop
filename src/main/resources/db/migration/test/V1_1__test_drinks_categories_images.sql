--заполнение категорий
INSERT INTO category(category_name) VALUES('горячие напитки');
INSERT INTO category(category_name) VALUES('холодные напитки');
INSERT INTO category(category_name) VALUES('кофе');
INSERT INTO category(category_name) VALUES('чай');
INSERT INTO category(category_name) VALUES('молочные коктейли');

--заполнение напитков
INSERT INTO drink(name, price, volume, description) VALUES('Эспрессо', 100, 50, 'Наш эспрессо с богатым насыщенным вкусом и карамельными сладкими нотками.');
INSERT INTO drink(name, price, volume, description) VALUES('Американо', 150, 150, 'Черный кофе на основе эспрессо с добавлением горячей воды.');
INSERT INTO drink(name, price, volume, description) VALUES('Латте', 180, 200, 'Нежное пропаренное молоко, богатый вкус эспрессо и тонкий слой молочной пены, завершающий напиток.');
INSERT INTO drink(name, price, volume, description) VALUES('Капучино', 180, 200, 'Это классика в кофейном мире. Эспрессо, пропаренное молоко, большое количество шелковистой воздушной молочной пены.');
INSERT INTO drink(name, price, volume, description) VALUES('Мокка', 200, 200, 'Эспрессо, соус из темного шоколада, пропаренное молоко. Сверху напиток украшается взбитыми сливками. Напиток может быть приготовлен в горячем и охлажденном варианте.');
INSERT INTO drink(name, price, volume, description) VALUES('Пунш Черешня', 180, 250, 'Пряный согревающий напиток со вкусом спелой вишни');
INSERT INTO drink(name, price, volume, description) VALUES('Зеленый чай', 90, 250, 'Аромантый зеленый чай.');
INSERT INTO drink(name, price, volume, description) VALUES('Черный чай', 90, 250, 'Аромантый черный чай.');
INSERT INTO drink(name, price, volume, description) VALUES('Молочный коктейль', 250, 250, 'Десертный напиток на основе молока и мороженого.');
INSERT INTO drink(name, price, volume, description) VALUES('Мятный лимонад', 170, 250, 'Прохладный напиток со льдом и самыми популярными яркими вкусами на ваш выбор!');

--TODO: заполнить базу
--заполнение связки напиток-категория
INSERT INTO drink_category(drink_id, category_id) VALUES(1, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(1, 3);
INSERT INTO drink_category(drink_id, category_id) VALUES(2, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(2, 3);
INSERT INTO drink_category(drink_id, category_id) VALUES(3, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(3, 3);
INSERT INTO drink_category(drink_id, category_id) VALUES(4, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(4, 3);
INSERT INTO drink_category(drink_id, category_id) VALUES(5, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(5, 3);
INSERT INTO drink_category(drink_id, category_id) VALUES(5, 4);
INSERT INTO drink_category(drink_id, category_id) VALUES(6, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(7, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(7, 4);
INSERT INTO drink_category(drink_id, category_id) VALUES(8, 1);
INSERT INTO drink_category(drink_id, category_id) VALUES(8, 4);
INSERT INTO drink_category(drink_id, category_id) VALUES(9, 2);
INSERT INTO drink_category(drink_id, category_id) VALUES(9, 5);
INSERT INTO drink_category(drink_id, category_id) VALUES(10, 2);

--заполнение картинок
INSERT INTO drink_img(image, drink_id) VALUES('espresso', 1);
INSERT INTO drink_img(image, drink_id) VALUES('americano', 2);
INSERT INTO drink_img(image, drink_id) VALUES('latte', 3);
INSERT INTO drink_img(image, drink_id) VALUES('cappuccino', 4);
INSERT INTO drink_img(image, drink_id) VALUES('mocha', 5);
INSERT INTO drink_img(image, drink_id) VALUES('punch', 6);
INSERT INTO drink_img(image, drink_id) VALUES('green_tea', 7);
INSERT INTO drink_img(image, drink_id) VALUES('black_tea', 8);
INSERT INTO drink_img(image, drink_id) VALUES('milkshake', 9);
INSERT INTO drink_img(image, drink_id) VALUES('mint_lemonade', 10);


