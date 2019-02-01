--заполнение справочника с ролями
INSERT INTO role(code, name) VALUES('CUSTOMER', 'Покупатель');
INSERT INTO role(code, name) VALUES('SALESMAN', 'Продавец');

--заполнение справочника со статусами заказа
INSERT INTO order_status(code, status_name) VALUES('IN_CART','В корзине');
INSERT INTO order_status(code, status_name) VALUES('PLACED','Размещен');
INSERT INTO order_status(code, status_name) VALUES('REJECTED','Отменен');
INSERT INTO order_status(code, status_name) VALUES('DELIVERED','Доставлен');

