package by.tech.project_management_app.service;

import by.tech.project_management_app.dto.TaskDto;
import by.tech.project_management_app.entities.Task;

public interface TaskService {
    Task findById(int id) throws ServiceException;

    void edit(TaskDto taskDto, int id, int userId) throws ServiceException, ValidationException;

    void delete(int id) throws ServiceException;

    Task create(TaskDto taskDto, int userId) throws ValidationException;
}