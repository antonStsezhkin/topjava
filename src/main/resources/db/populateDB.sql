DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
-- Populate users (1 admin, 5 registered users.)
INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin'),
       ('User', 'user@yandex.ru', 'password');
--        ('Vasya', 'vasya@yandex.ru', 'password'),
--        ('Masha', 'masha@yandex.ru', 'password');
--
-- INSERT INTO users (name, email, password, calories_per_day)
-- VALUES
--     -- Users with special diets
--     ('Maya', 'mplis@gmail.com', 'password', 1500),
--     ('Valuev', 'val@gmail.com', 'password', 5000);

-- Distribute roles to users
INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 100000)
     , ('USER', 100001);
--      , ('USER', 100002)
--      , ('USER', 100003)
--      , ('USER', 100004)
--      , ('USER', 100005);

-- Create meals for registered users. Admin does not need food :)
INSERT INTO meals (description, calories, time, user_id)
VALUES
     -- ADMIN
    ('Завтрак', 500, '2020-03-23 08:00', 100000)
     , ('Обед', 1000, '2020-03-23 13:00', 100000)
     , ('Ужин', 500, '2020-03-23 20:00', 100000)
     -- USER
     , ('Завтрак', 500, '2020-03-23 08:00', 100001)
     , ('Обед', 1000, '2020-03-23 13:00', 100001)
     , ('Ужин', 500, '2020-03-23 20:00', 100001)
--      -- Vasya
--      , ('Завтрак', 500, '2020-03-23 08:00', 100002)
--      , ('Обед', 1000, '2020-03-23 13:00', 100002)
--      , ('Ужин', 500, '2020-03-23 20:00', 100002)
--     -- Masha
--      , ('Завтрак', 500, '2020-03-23 08:00', 100003)
--      , ('Обед', 1000, '2020-03-23 13:00', 100003)
--      , ('Ужин', 500, '2020-03-23 20:00', 100003)
--      -- Maya
--      , ('Завтрак', 500, '2020-03-23 08:00', 100004)
--      , ('Обед', 1000, '2020-03-23 13:00', 100004)
--      , ('Ужин', 500, '2020-03-23 20:00', 100004)
--      -- Valuev
--      , ('Завтрак', 500, '2020-03-23 08:00', 100005)
--      , ('Обед', 2500, '2020-03-23 13:00', 100005)
--      , ('Ужин', 500, '2020-03-23 20:00', 100005)
