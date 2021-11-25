package by.tech.project_management_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import by.tech.project_management_app.dto.TaskDto;
import by.tech.project_management_app.entities.SecurityUser;
import by.tech.project_management_app.entities.Task;
import by.tech.project_management_app.service.ProjectService;
import by.tech.project_management_app.service.ServiceException;
import by.tech.project_management_app.service.TaskService;
import by.tech.project_management_app.service.ValidationException;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private TaskService taskService;
    private ProjectService projectService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public Task findTaskById(@PathVariable int id) {
        try {
            return taskService.findById(id);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task is not found", ex);
        }
    }

    @PostMapping()
    public Task createTask(@RequestBody TaskDto taskDto) {
        int userId = getUserIdFromAuthentication();
        try {
            projectService.checkIfProjectExists(taskDto.getProjectId());
            return taskService.create(taskDto, userId);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Task is not created.", ex);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Task is not created. One or more fileds are invalid", ex);
        }
    }

    @PatchMapping("/{id}")
    public void editTask(@RequestBody TaskDto taskDto, @PathVariable int id) {
        int userId = getUserIdFromAuthentication();
        try {
            projectService.checkIfProjectExists(taskDto.getProjectId());
            taskService.edit(taskDto, id, userId);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task or project with provided id is not found", ex);
        } catch (ValidationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Task is not edited. One or more fileds are invalid", ex);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            taskService.delete(id);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with provided id is not found", ex);
        }
    }

    private int getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.getUserId();
    }
}