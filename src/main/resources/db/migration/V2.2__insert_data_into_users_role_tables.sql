insert into roles (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) values
('Admin', '/', TRUE , TRUE, TRUE, TRUE),
('Manager', '/depts,/departments,/employees,/ems,/acnts,/accounts', TRUE, TRUE, TRUE, FALSE),
('user', '/employees,/ems,/acnts,/accounts', TRUE, FALSE, FALSE, FALSE)
;
commit;


INSERT INTO checking_account (account_balance, account_number)
VALUES
    (1500.00, 100001),
    (2200.50, 100002),
    (500.75, 100003),
    (3200.25, 100004),
    (900.00, 100005),
    (1800.50, 100006),
    (750.25, 100007),
    (4200.75, 100008),
    (1500.00, 100009),
    (3200.25, 100010);
commit;

INSERT INTO savings_account (account_balance, account_number)
VALUES
    (2500.00, 200001),
    (3800.75, 200002),
    (1200.50, 200003),
    (4500.25, 200004),
    (1800.00, 200005),
    (3200.50, 200006),
    (800.25, 200007),
    (6000.75, 200008),
    (2800.00, 200009),
    (5200.25, 200010);
commit;

INSERT INTO users (first_name, last_name, email, username, password, secret_key, phone, enabled, checking_account_id, savings_account_id)
VALUES
    ('John', 'Doe', 'john@example.com', 'johndoe', 'password1', 'secret_key1', '1234567890', B'1', 1, 1),
    ('Jane', 'Smith', 'jane@example.com', 'janesmith', 'password2', 'secret_key2', '9876543210', B'1', 2, 2),
    ('Alice', 'Johnson', 'alice@example.com', 'alicej', 'password3', 'secret_key3', '5555555555', B'1', 3, 3),
    ('Robert', 'Williams', 'robert@example.com', 'robertw', 'password4', 'secret_key4', '7777777777', B'1', 4, 4),
    ('Emily', 'Brown', 'emily@example.com', 'emilyb', 'password5', 'secret_key5', '1111111111', B'1', 5, 5),
    ('Michael', 'Jones', 'michael@example.com', 'michaelj', 'password6', 'secret_key6', '2222222222', B'1', 6, 6),
    ('Maria', 'Garcia', 'maria@example.com', 'mariag', 'password7', 'secret_key7', '3333333333', B'1', 7, 7),
    ('David', 'Lee', 'david@example.com', 'davidl', 'password8', 'secret_key8', '4444444444', B'1', 8, 8),
    ('Sophia', 'Martinez', 'sophia@example.com', 'sophiam', 'password9', 'secret_key9', '5555555555', B'1', 9, 9),
    ('Daniel', 'Miller', 'daniel@example.com', 'danielm', 'password10', 'secret_key10', '6666666666', B'1', 10, 10);
commit;


insert into users_roles values
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 3),
(7, 3),
(8, 3),
(9, 3),
(10, 3),
(1, 2),
(1, 3)
;
commit;

INSERT INTO appointment (confirmed, date, description, location, user_id)
VALUES
    (B'1', '2023-08-14 10:00:00', 'Meeting with client', 'Conference Room A', 6), -- Client user
    (B'0', '2023-08-15 15:30:00', 'Follow-up call', 'Remote', 7), -- Client user
    (B'1', '2023-08-16 14:00:00', 'Project brainstorming', 'Office Lobby', 8), -- Client user
    (B'1', '2023-08-17 11:30:00', 'Team status update', 'Meeting Room B', 9), -- Client user
    (B'0', '2023-08-18 09:00:00', 'Training session', 'Training Room C', 10), -- Client user
    (B'1', '2023-08-19 13:45:00', 'Product demo', 'Client Site', 1), -- Manager user
    (B'0', '2023-08-20 16:00:00', 'Technical discussion', 'Virtual', 2), -- Admin user
    (B'1', '2023-08-21 10:30:00', 'Budget review', 'Office Conference Room', 3), -- Regular user
    (B'1', '2023-08-22 12:15:00', 'Marketing presentation', 'Marketing Dept', 4), -- Regular user
    (B'1', '2023-08-23 14:30:00', 'Strategy meeting', 'Boardroom', 5); -- Regular user
commit;
