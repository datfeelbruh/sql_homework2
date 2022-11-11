INSERT INTO app_user (name, password, created_at)
VALUES
('Dima', 'root', CURRENT_TIMESTAMP),
('Boroda', 'toor', CURRENT_TIMESTAMP + INTERVAL '10 MINUTE 10 SECOND'),
('Shrek', 'shrek', CURRENT_TIMESTAMP + INTERVAL '20 MINUTE 20 SECOND'),
('Acetate', 'mypass', CURRENT_TIMESTAMP + INTERVAL '30 MINUTE 30 SECOND'),
('Nippel', 'army23', CURRENT_TIMESTAMP+ INTERVAL '40 MINUTE 40 SECOND');

INSERT INTO app_user_post (text, created_at, user_id)
VALUES
('Хорошо поспал', CURRENT_TIMESTAMP + INTERVAL '1 HOUR', 1),
('Вкусно поел', CURRENT_TIMESTAMP + INTERVAL '2 HOUR', 1),
('Сделал homework', CURRENT_TIMESTAMP + INTERVAL '3 HOUR', 1),
('Неплохо покодил', CURRENT_TIMESTAMP + INTERVAL '4 HOUR', 2),
('Отлично поиграл', CURRENT_TIMESTAMP + INTERVAL '5 HOUR', 3),
('Сходил в магазин', CURRENT_TIMESTAMP + INTERVAL '6 HOUR', 4),
('Вернулся из армии!', CURRENT_TIMESTAMP + INTERVAL '7 HOUR', 5),
('Семья встречает', CURRENT_TIMESTAMP + INTERVAL '8 HOUR', 5);

INSERT INTO app_user_comment (text, post_id, user_id, created_at)
VALUES
('Cколько спал?', 1, 2, CURRENT_TIMESTAMP + INTERVAL '1 DAY 10 MINUTE'),
('Когда уснул?', 1, 3, CURRENT_TIMESTAMP + INTERVAL '1 DAY 20 MINUTE'),
('Что ел?', 2, 4, CURRENT_TIMESTAMP + INTERVAL '1 DAY 30 MINUTE'),
('Пельмени', 2, 1, CURRENT_TIMESTAMP + INTERVAL '1 DAY 40 MINUTE'),
('Что купил?', 6, 5, CURRENT_TIMESTAMP + INTERVAL '1 DAY 50 MINUTE'),
('Поздравляю', 7, 4, CURRENT_TIMESTAMP + INTERVAL '1 DAY 1 HOUR'),
('Что кодил?', 4, 1, CURRENT_TIMESTAMP + INTERVAL '1 DAY 2 HOUR');

INSERT INTO app_user_like (user_id, post_id, comment_id)
VALUES
(2, 2, 1),
(3, 2, 1),
(4, 2, 1),
(2, 2, 2),
(3, 2, 2),
(1, 7, 6),
(2, 7, 6),
(3, 7, 6),
(4, 7, 6),
(2, 3, 4),
(3, 3, 4),
(4, 3, 4),
(4, 3, NULL),
(4, NULL, 3);



