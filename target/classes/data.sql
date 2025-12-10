INSERT INTO users (id, name, surnames, username, phone_number, email, password, rol)
VALUES (1, 'usuario', 'usuario', 'usuario', '+34111111111', 'user@example.com', '$2a$10$xkNM.N2UiMlUP.uQjCvN6.rUi22jOsQo/6PmAY9yIVjSu6VzkT6ty', 'USUARIO');

INSERT INTO tasks (id, title, description, date, user_id, status)
VALUES
    (1, 'MemoWorks', 'realizar proyecto MemoWorks', '2025-12-18 10:30:00', 1, TRUE),
    (2, 'trabajo de diseño', 'realizar trabajo de diseño', '2025-12-18 10:30:00', 1, TRUE);

INSERT INTO task_labels (task_id, labels) VALUES
                                              (1, 'DWEC'),
                                              (1, 'DWES'),
                                              (1, 'DIW'),
                                              (1, 'DAW'),
                                              (2, 'DIW');
