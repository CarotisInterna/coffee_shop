--create table with roles
CREATE TABLE IF NOT EXISTS role (
  id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(20) NOT NULL UNIQUE
);

--create table with application users
CREATE TABLE IF NOT EXISTS app_user (
  id SERIAL NOT NULL PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  full_name VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone_number VARCHAR(11) NOT NULL UNIQUE,
  address TEXT NOT NULL,
  role_id INT REFERENCES role (id)
);

--create table drink category
CREATE TABLE IF NOT EXISTS category (
  id SERIAL NOT NULL PRIMARY KEY,
  category_name VARCHAR(20) NOT NULL UNIQUE
);

--create table with drinks
CREATE TABLE IF NOT EXISTS drink (
  id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  price REAL NOT NULL CHECK (price > 0),
  volume SMALLINT NOT NULL CHECK (volume > 0),
  description TEXT,
  CONSTRAINT uc_name_volume UNIQUE (name, volume)
);

--create table with drink category
CREATE TABLE IF NOT EXISTS drink_category (
  drink_id INT NOT NULL REFERENCES drink (id),
  category_id INT NOT NULL REFERENCES category (id)
);

--create table with drink image
CREATE TABLE IF NOT EXISTS drink_img (
  id SERIAL NOT NULL PRIMARY KEY,
  image TEXT NOT NULL,
  drink_id INT NOT NULL REFERENCES drink (id)
);

--create table with toppings
CREATE TABLE IF NOT EXISTS topping (
  id SERIAL NOT NULL PRIMARY KEY,
  name VARCHAR(40) NOT NULL UNIQUE,
  price REAL NOT NULL CHECK (price > 0)
);


--create table with order statuses
CREATE TABLE IF NOT EXISTS order_status (
  id SERIAL NOT NULL PRIMARY KEY,
  status_name VARCHAR(20) NOT NULL UNIQUE
);

--create table with orders
CREATE TABLE IF NOT EXISTS orders (
  id SERIAL NOT NULL PRIMARY KEY,
  status_id INT NOT NULL REFERENCES order_status (id),
  customer_id INT NOT NULL REFERENCES app_user (id),
  total REAL NOT NULL CHECK (total > 0),
  address TEXT NOT NULL,
  date TIMESTAMP NOT NULL
);

--create table with ordered drinks
CREATE TABLE IF NOT EXISTS drink_order (
  id SERIAL NOT NULL PRIMARY KEY,
  order_id INT NOT NULL REFERENCES orders (id),
  drink_id INT NOT NULL REFERENCES drink (id),
  quantity SMALLINT NOT NULL CHECK (quantity > 0 AND quantity < 51)
);

--create table with customer's drink configs
CREATE TABLE IF NOT EXISTS topping_for_drink_in_order (
  drink_order_id INT NOT NULL REFERENCES drink_order (id),
  topping_id INT NOT NULL REFERENCES topping (id),
  quantity SMALLINT NOT NULL CHECK (quantity > 0 AND quantity < 4),
  CONSTRAINT uc_topping_in_drink UNIQUE (drink_order_id, topping_id)
);

--create table with orders and items
-- CREATE TABLE IF NOT EXISTS order_drink_topping (
--   order_id INT NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
--   drink_topping_id INT NOT NULL REFERENCES drink_topping (id) ON DELETE CASCADE,
--   quantity SMALLINT NOT NULL CHECK (quantity > 0 AND quantity < 51)
-- );

--create table with ordered drinks
-- CREATE TABLE IF NOT EXISTS order_drink (
--   id SERIAL NOT NULL PRIMARY KEY,
--   order_id INT NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
--   drink_id INT NOT NULL REFERENCES drink (id) ON DELETE CASCADE,
--   quantity SMALLINT NOT NULL CHECK (quantity > 0 AND quantity < 51)
-- );
--
-- --create table with toppings for ordered drinks
-- CREATE TABLE IF NOT EXISTS toppings_for_ordered_drinks (
--   order_drink_id INT NOT NULL REFERENCES order_drink (id) ON DELETE CASCADE,
--   topping_id INT NOT NULL REFERENCES topping (id) ON DELETE CASCADE,
--   quantity SMALLINT NOT NULL CHECK (quantity > 0 AND quantity < 5),
--   CONSTRAINT uc_drink_with_topping_in_order UNIQUE (order_drink_id, topping_id)
-- );
--create table with drinks and toppings selected by customer
-- CREATE TABLE IF NOT EXISTS drink_topping (
--   id SERIAL NOT NULL PRIMARY KEY,
--   drink_id INT NOT NULL REFERENCES drink (id) ON DELETE CASCADE,
--   topping_id INT NOT NULL REFERENCES topping (id) ON DELETE CASCADE,
--   quantity SMALLINT NOT NULL CHECK (quantity > 0 AND quantity < 4)
-- );
