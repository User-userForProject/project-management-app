package by.tech.project_management_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.tech.project_management_app.dto.TaskDto;
import by.tech.project_management_app.entities.Task;
import by.tech.project_management_app.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    private static final boolean IS_NOT_DELETED = true;
    private static final boolean IS_DONE = false;
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task findById(int id) throws ServiceException {
        try {
            return taskRepository.findById(id);
        } catch (Exception ex) {
            throw new ServiceException("Task isn't found");
        }
    }

    @Override
    public Task create(TaskDto taskDto, int userId) throws ValidationException {
        Validator.validateTaskDto(taskDto);
        Task task = new Task();
        task.setTaskName(taskDto.getTaskName());
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setCreatedBy(userId);
        task.setModifiedBy(userId);
        task.setDeleted(IS_NOT_DELETED);
        task.setDone(IS_DONE);
        task.setProjectId(taskDto.getProjectId());
        return taskRepository.create(task);
    }

    @Override
    public void edit(TaskDto taskDto, int id, int userId) throws ServiceException, ValidationException {
        Validator.validateTaskDto(taskDto);
        try {
            checkIfTaskExists(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Task is not found");
        }
        Task task = new Task();
        task.setTaskName(taskDto.getTaskName());
        task.setTaskDescription(taskDto.getTaskDescription());
        task.setModifiedBy(userId);
        task.setDone(taskDto.isDone());
        task.setId(id);
        taskRepository.update(task);
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            checkIfTaskExists(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Task is not found");
        }
        taskRepository.deleteById(id);
    }

    private void checkIfTaskExists(int id) throws ServiceException {
        int countProject = taskRepository.countTaskId(id);
        if (countProject == 0) {
            throw new ServiceException();
        }
    }

}