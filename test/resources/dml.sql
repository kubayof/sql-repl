INSERT INTO products (product_name)
VALUES ('Milk'),
       ('Apples'),
       ('Bananas'),
       ('Bread'),
       ('Eggs');

INSERT INTO customers (first_name, last_name)
VALUES ('First', 'User'),
       ('Second', 'One');

INSERT INTO sales (customer_id, product_id, sale_date)
VALUES (1, 1, '2020-01-01 14:00:01'),
       (1, 2, '2020-02-01 17:10:01'),
       (1, 4, '2020-04-01 11:00:01'),
       (2, 3, '2020-04-01 12:00:01'),
       (2, 3, '2020-04-01 10:00:01'),
       (2, 4, '2020-05-01 12:00:01'),
       (2, 1, '2020-05-01 11:00:01');
