# Role
INSERT INTO role VALUES (0,'ADMIN');
INSERT INTO role VALUES (1,'MANAGER');
INSERT INTO role VALUES (2,'EMPLOYEE');
INSERT INTO role VALUES (3,'CLERK');

# Admin
INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1001, '1975-10-06', 'awiegand@lockman.com', 'Awie', '$2a$10$sq0QegpU6YzPsDE45CdN/u.3CNGiqMewrsufHgiXOXjKn1ie.af56', '+8082131129672', 'https://lorempixel.com/640/480/?23679', 'awiegand75', 0);

INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1002, '1984-05-12', 'axel.littel@purdy.info', 'Axel', '$2a$10$e5jNeECzTDYBczntWW4dM.KMMG.4ZzDe.BXm2tngrd/HQtEhktV5m', '+8875995329604', 'https://lorempixel.com/640/480/?87106', 'axellittel84', 0);

# Manager
INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1003, '1973-08-11', 'lela56@spinka.com',  'Lela', '$2a$10$D84LgxAHNrYHsNiwa0KnXevmi.2w9kBHAqzW55K95Kc.lprRa29EO', '+2496941427791', 'https://lorempixel.com/640/480/?90864', 'lela56', 1);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1004, '2002-04-26', 'simon@jfeest.biz', 'Simon', '$2a$10$OQHBfJZRnGGTQeQz5jjs5OaY1Y9ua4OFKsR17Ukw3l2R2i3v.fi4i', '+7391877807420', 'https://lorempixel.com/640/480/?67778', 'simon02', 1);

#Employee
INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1005, '2011-03-13', 'gisselle64@heidenreich.net', 'Gisselle', '$2a$10$J6ovbO5rShdYjLLqZNO2F.ijzsc.f82PFY4XemGtZjhwSnr8qYkVa', 1003, '+7989250235708', 'https://lorempixel.com/640/480/?93796', 'gisselle11', 2);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1006, '2006-11-03', 'rosalyn.mcglynn@terry.com', 'Rosalyn', '$2a$10$h0A7xXateyhNuQBkbllOiu6rgMN35UC5H7YAo1K8LQFFLp8.rQuYa', 1003, '+5264893481496', 'https://lorempixel.com/640/480/?65768', 'rosalyn06', 2);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1007, '2007-02-17', 'michele48@shields.net', 'Michele', '$2a$10$8X5NOaC82osXBpes.7qHFusHUUnevFbQZVgGotP9zx07PwwFM7jeC', 1004, '+7543273225450', 'https://lorempixel.com/640/480/?20946', 'michele07', 2);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1008, '2016-10-26', 'francisca.grady@yundt.com', 'Francisca', '$2a$10$j3hXXevS3RcdfWFZoKoxa.YAETFfGKv6Mm4DQVXLoipHdyTB6puuC', 1004, '+3537218932321', 'https://lorempixel.com/640/480/?42975', 'francisca16', 2);


#Clerk
INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1009, '1978-04-08', 'aufderhar.justen@borer.com', 'Justen', '$2a$10$yXGC/XKDFfsFolCO3Wh/C.FyeyuxaT2wwT2337/qN.rFZ5rYnKN9O', '+3875008166143', 'https://lorempixel.com/640/480/?82793', 'aufderharjusten78', 3);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1010, '1986-02-05', 'trussel@bernhard.com', 'Trussel', '$2a$10$Z2pH/5o5LA72OSCbwxCUMehA6uACE.z1QS3Z3NTYY.6qQiLeMNamG', '+1329972782864', 'https://lorempixel.com/640/480/?83303', 'trussel86', 3);

#Default
INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1111, '1111-01-01', 'admin@helloinven.com', 'Admin', '$2a$10$NTzFENtc5jPsIrNzrfQ.DOG.YprkWRuMoWD/LqDYRVAvzh/p.HaEq', '111111111', 'Name-tag-admin-1000.png', 'admin', 0);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (2222, '2222-02-02', 'manager@helloinven.com', 'Manager', '$2a$10$Kk0QEMJ5JzMc9f4VZzAQs.wSC2CVeAxcNYgsEX6AckbS.dbse9HcK', '222222222', '0812-manager.jpg', 'manager', 1);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (3333, '3333-03-03', 'emp@helloinven.com', 'Employee', '$2a$10$v27p9JiLSTlnCnx4tyRtMe8Nrrb6ipsI1lQ5akPYFdAdWrYEPZAIm', 2222, '333333333', 'home-masthead-1.jpg', 'emp', 2);

INSERT INTO USER(emp_id, emp_birthday, emp_email,  emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (4444, '4444-04-04', 'clerk@helloinven.com', 'Clerk', '$2a$10$d7urmcjgZBmQCekZvvMx7.NTA4S34.vjbSTjLp1JWDT6Oj2ncj5R6', '444444444', 'Clerk_image.jpg', 'clerk', 3);
# Password di BCrypt dahulu `helloinven2`
# Password = emp_name huruf kecilsemua

# Category
INSERT INTO CATEGORY(created_at, updated_at, category_name, category_description)
VALUES ('2018-12-18 22:29:36', '2018-12-18 22:29:36', 'Furniture', 'furniture inside rooms');

INSERT INTO CATEGORY(created_at, updated_at, category_name, category_description)
VALUES ('2018-12-18 22:29:36', '2018-12-18 22:29:36', 'Board', 'board used in rooms');

# Item
INSERT INTO ITEM(item_id, created_at, updated_at, depth, height, image_path, item_type, NAME, price, quantity, weight, width, category_id)
VALUES (60214159, '2018-12-27 11:55:08', '2018-12-27 11:55:08', 65, 73, 'malm-desk-white__0416654_pe573954_s4.jpg', 1, 'Malm Desk', 95, 3, 50, 140, 1)

INSERT INTO ITEM(item_id, created_at, updated_at, depth, height, image_path, item_type, NAME, price, quantity, weight, width, category_id)
VALUES (80213074, '2018-12-28 11:55:08', '2018-12-28 11:55:08', 50, 75, 'micke-desk-white__0123483_pe279640_s4', 1, 'Micke Desk', 60, 3, 50, 105, 1)

INSERT INTO ITEM(item_id, created_at, updated_at, depth, height, image_path, item_type, NAME, price, quantity, weight, width, category_id)
VALUES (50380347, '2019-01-02 15:26:20', '2019-01-02 15:26:20', 0, 3, 'målarna-blackboard-planner__0534210_pe649049_s4.jpg', 1, 'MÅLARNA  Blackboard planner', 30, 1, 3.34, 61, 2)

# Item Serial