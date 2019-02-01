--заполнение справочника с ролями
INSERT INTO role(name) VALUES('Покупатель');
INSERT INTO role(name) VALUES('Продавец');

--заполнение справочника со статусами заказа
INSERT INTO order_status(status_name) VALUES('В корзине');
INSERT INTO order_status(status_name) VALUES('Размещен');
INSERT INTO order_status(status_name) VALUES('Отменен');
INSERT INTO order_status(status_name) VALUES('Доставлен');

