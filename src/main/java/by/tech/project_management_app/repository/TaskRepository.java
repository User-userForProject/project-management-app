package by.tech.project_management_app.repository;

import by.tech.project_management_app.entities.Task;


public interface TaskRepository {
    Task create(Task task);
    Task findById(int id);
    void update(Task task);
    void deleteById(int id);
    int countTaskId(int id);
}