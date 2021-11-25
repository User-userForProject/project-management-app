CREATE TABLE IF NOT EXISTS users (
id SERIAL,
username VARCHAR(45) NOT NULL,
password VARCHAR(60) NOT NULL,
enabled INT NOT NULL,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS authorities (
id SERIAL,
authority VARCHAR(45) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS users_authorities (
user_id INT NOT NULL,
authority_id INT NOT NULL,
PRIMARY KEY (user_id, authority_id),
CONSTRAINT fk_user
	FOREIGN KEY(user_id) 
	REFERENCES users(id),
CONSTRAINT fk_authority
	FOREIGN KEY(authority_id) 
	REFERENCES authorities(id));

CREATE TABLE IF NOT EXISTS projects (
id SERIAL,
name VARCHAR(45) NOT NULL,
description VARCHAR(1000) NOT NULL,
createdBy INT NOT NULL,
modifiedBy INT NOT NULL,
isDeleted INT NOT NULL,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS users_projects (
user_id INT NOT NULL,
project_id INT NOT NULL,
PRIMARY KEY (user_id, project_id),
CONSTRAINT fk_user
	FOREIGN KEY(user_id) 
	REFERENCES users(id),
CONSTRAINT fk_project
	FOREIGN KEY(project_id) 
	REFERENCES projects(id));

CREATE TABLE IF NOT EXISTS tasks (
id SERIAL,
name VARCHAR(45) NOT NULL,
description VARCHAR(1000) NOT NULL,
createdBy INT NOT NULL,
modifiedBy INT NOT NULL,
isDeleted INT NOT NULL,
isDone INT NOT NULL,
project_id INT NOT NULL,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS users_tasks (
user_id INT NOT NULL,
task_id INT NOT NULL,
PRIMARY KEY (user_id, task_id),
CONSTRAINT fk_user
	FOREIGN KEY(user_id) 
	REFERENCES users(id),
CONSTRAINT fk_task
	FOREIGN KEY(task_id) 
	REFERENCES tasks(id));