DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;

CREATE TABLE products
(
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(30) NOT NULL
);

CREATE TABLE customers
(
    customer_id SERIAL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL
);

CREATE TABLE sales
(
    sale_id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    sale_date timestamp,
    foreign key (product_id) references products(product_id),
    foreign key (customer_id) references customers(customer_id)
);