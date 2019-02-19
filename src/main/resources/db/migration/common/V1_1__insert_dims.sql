--заполнение справочника с ролями
INSERT INTO role(code, name) VALUES('USER', 'USER');
INSERT INTO role(code, name) VALUES('VENDOR', 'VENDOR');

--заполнение справочника со статусами заказа
INSERT INTO order_status(code, status_name) VALUES('IN_CART','В корзине');
INSERT INTO order_status(code, status_name) VALUES('PLACED','Размещен');
INSERT INTO order_status(code, status_name) VALUES('REJECTED','Отменен');
INSERT INTO order_status(code, status_name) VALUES('DELIVERED','Доставлен');

