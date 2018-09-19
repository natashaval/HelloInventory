INSERT INTO role VALUES (0,'ADMIN');
INSERT INTO role VALUES (1,'MANAGER');
INSERT INTO role VALUES (2,'EMPLOYEE');
INSERT INTO role VALUES (3,'CLERK');

# Admin
INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1001, '1975-10-06', 'awiegand@lockman.com', 1, 'Awie', '$2a$10$sq0QegpU6YzPsDE45CdN/u.3CNGiqMewrsufHgiXOXjKn1ie.af56', '+8082131129672', 'https://lorempixel.com/640/480/?23679', 'awiegand75', 0);

INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1002, '1984-05-12', 'axel.littel@purdy.info', 1, 'Axel', '$2a$10$e5jNeECzTDYBczntWW4dM.KMMG.4ZzDe.BXm2tngrd/HQtEhktV5m', '+8875995329604', 'https://lorempixel.com/640/480/?87106', 'axellittel84', 0);

# Manager
INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1003, '1973-08-11', 'lela56@spinka.com', 1, 'Lela', SHA1('lela'), '+2496941427791', 'https://lorempixel.com/640/480/?90864', 'lela56', 1);

INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1004, '2002-04-26', 'jfeest@simonis.biz', 1, 'Simonis', SHA1('simonis'), '+7391877807420', 'https://lorempixel.com/640/480/?67778', 'simonis02', 1);

#Employee
INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1005, '2011-03-13', 'gisselle64@heidenreich.net', 1, 'Gisselle', SHA1('gisselle'), 1003, '+7989250235708', 'https://lorempixel.com/640/480/?93796', 'gisselle11', 2);

INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1006, '2006-11-03', 'rosalyn.mcglynn@terry.com', 1, 'Rosalyn', SHA1('rosalyn'), 1003, '+5264893481496', 'https://lorempixel.com/640/480/?65768', 'rosalyn06', 2);

INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1007, '2007-02-17', 'michele48@shields.net', 1, 'Michele', SHA1('michele'), 1004, '+7543273225450', 'https://lorempixel.com/640/480/?20946', 'michele07', 2);

INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_manager_id, emp_phone, emp_photo, username, role_id)
VALUES (1008, '2016-10-26', 'francisca.grady@yundt.com', 1, 'Francisca', SHA1('francisca'), 1004, '+3537218932321', 'https://lorempixel.com/640/480/?42975', 'francisca16', 2);


#Clerk
INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1009, '1978-04-08', 'aufderhar.justen@borer.com', 1, 'Justen', SHA1('justen'), '+3875008166143', 'https://lorempixel.com/640/480/?82793', 'aufderharjusten78', 3);

INSERT INTO USER(emp_id, emp_birthday, emp_email, emp_active, emp_name, PASSWORD, emp_phone, emp_photo, username, role_id)
VALUES (1010, '1986-02-05', 'trussel@bernhard.com', 1, 'Trussel', SHA1('trussel'), '+1329972782864', 'https://lorempixel.com/640/480/?83303', 'trussel86', 3);

# Password di BCrypt dahulu https://www.browserling.com/tools/bcrypt
# Password = emp_name huruf kecilsemua



SELECT emp_name, username, role.role FROM USER JOIN role ON user.`role_id` = role.role_id
WHERE PASSWORD = SHA1('awie');

SELECT username, PASSWORD, emp_active FROM USER WHERE username='axellittel84'
SELECT u.username, r.role FROM USER u JOIN role r ON u.role_id = r.role_id WHERE u.username='axellittel84'