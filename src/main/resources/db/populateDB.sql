DELETE FROM votes;
DELETE FROM menu;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 1000;

INSERT INTO restaurants (name) VALUES
    'Restaurant1',
    'Restaurant2',
    'Restaurant3';

INSERT INTO menu (restaurant_id, name, price) VALUES
    (1003, 'Fish', 10),
    (1004, 'Salad', 5),
    (1004, 'Water', 1),
    (1005, 'Burger', 40),
    (1005, 'Potato', 10,
    (1005, 'IceCream', 20);

INSERT INTO users (name, email, password) VALUES
    ('Nataly', 'qweqwe@gmail.com', 'qweqwe'),
    ('Alisa', 'asdasd@gmail.com', 'asdasd911');

INSERT INTO user_roles (user_id, role) VALUES
    (1000, 'ROLE_ADMIN'),
    (1001, 'ROLE_USER');

INSERT INTO votes (user_id, restaurant_id, voting_date) VALUES
    (1000, 1003, '2019-08-14'),
    (1001, 1004, '2019-08-14');


