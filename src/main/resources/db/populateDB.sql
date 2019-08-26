DELETE FROM votes;
DELETE FROM meals;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 1000;

INSERT INTO users (name, email, password) VALUES
    ('Nataly', 'qweqwe@gmail.com', 'qweqwe'),
    ('Alisa', 'asdasd@gmail.com', 'asdasd911');

INSERT INTO user_roles (user_id, role) VALUES
(1000, 'ROLE_ADMIN'),
(1001, 'ROLE_USER');

INSERT INTO restaurants (name) VALUES
    ('Restaurant1'),
    ('Restaurant2'),
    ('Restaurant3');

INSERT INTO meals (restaurant_id, name, price, date) VALUES
    (1002, 'Fish', 10, '2019-08-19'),
    (1003, 'Salad', 5, '2019-08-19'),
    (1003, 'Water', 1, '2019-08-19'),
    (1004, 'Burger', 40, '2019-08-18'),
    (1004, 'Potato', 10, '2019-08-18'),
    (1004, 'IceCream', 20, '2019-08-18');

INSERT INTO votes (user_id, restaurant_id, voting_date) VALUES
    (1000, 1003, '2019-08-20'),
    (1000, 1002, '2019-08-21'),
    (1001, 1002, '2019-08-20');


