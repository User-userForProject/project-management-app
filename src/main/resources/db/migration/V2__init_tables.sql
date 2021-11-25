INSERT INTO users (id, username, password, enabled)
VALUES 
(1, 'John', '$2a$12$NJAWVH4Pq8kQulULSYU/D.nyNeWtaI/n2oA0xKRyr9jmh.IW8Y4cu', 1),
(2, 'Bob', '$2a$12$ZrIxe06U4xu8bvVabIDi1uV5LBzBeWEv/Jm0SX6waTTt/ntUMuI/i', 1),
(3, 'Liza', '$2a$12$KzJtgZox09TuXvLu/gj49OtLQswhEvd2dbyqQT4q4ztY72dpk9t8q', 1),
(4, 'Mary', '$2a$12$AETkEdaN0FPCSKuxhtUkCOx2TKq3/GIgrFFYWee2zIaRldgPLlj3u', 1);

INSERT INTO authorities (id, authority)
VALUES 
(1, 'ADMIN'),
(2, 'USER');

INSERT INTO users_authorities (user_id, authority_id)
VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 2);

INSERT INTO projects (id, name, description, createdBy, modifiedBy, isDeleted)
VALUES
(1, 'Project 1', 'Simple project for managing other projects', 1, 1, 0),
(2, 'Project 2', 'User management system', 1, 1, 0),
(3, 'Project 3', 'Work-related project', 1, 1, 0),
(4, 'Project 4', 'Web-site for renting cars', 1, 1, 0),
(5, 'Project 5', 'Online games store', 2, 2, 0),
(6, 'Project 6', 'Simple project for managing other projects', 1, 1, 0),
(7, 'Project 7', 'User management system', 1, 1, 0),
(8, 'Project 8', 'Work-related project', 1, 1, 0),
(9, 'Project 9', 'Web-site for renting cars', 1, 1, 0),
(10, 'Project 10', 'Online games store', 2, 2, 0),
(11, 'Project 11', 'Simple project for managing other projects', 1, 1, 0),
(12, 'Project 12', 'User management system', 1, 1, 0),
(13, 'Project 13', 'Work-related project', 1, 1, 0),
(14, 'Project 14', 'Web-site for renting cars', 1, 1, 0),
(15, 'Project 15', 'Online games store', 2, 2, 0);

INSERT INTO users_projects (user_id,project_id)
VALUES
(2, 1),
(3, 1),
(4, 1),
(1, 1);

INSERT INTO tasks (id, name, description, createdBy, modifiedBy, isDeleted, isDone, project_id)
VALUES
(1, 'Task 1', 'Create user menu', 1, 1, 0, 0, 1),
(2, 'Task 2', 'Intialize database', 1, 1, 0, 0, 1),
(3, 'Task 3', 'Access with gmail', 1, 1, 0, 0, 1),
(4, 'Task 4', 'Create menu', 1, 1, 0, 0, 2),
(5, 'Task 5', 'Add users', 1, 1, 0, 0, 3),
(6, 'Task 6', 'Add navigation', 1, 1, 0, 0, 10),
(7, 'Task 7', 'DB optimization', 1, 1, 0, 0, 11);

INSERT INTO users_tasks (user_id, task_id)
VALUES
(2, 1),
(3, 2),
(4, 3),
(3, 4),
(1, 5),
(3, 6),
(2, 7);

SELECT setval(pg_get_serial_sequence('tasks', 'id'), coalesce(max(id),0) + 1, false) FROM tasks;
SELECT setval(pg_get_serial_sequence('projects', 'id'), coalesce(max(id),0) + 1, false) FROM projects;
SELECT setval(pg_get_serial_sequence('users', 'id'), coalesce(max(id),0) + 1, false) FROM users;