package by.tech.project_management_app.service;

import by.tech.project_management_app.dto.ProjectDto;
import by.tech.project_management_app.dto.TaskDto;

public class Validator {
    private static final int NAME_LENGTH = 45;
    private static final int DESCRIPTION_LENGTH = 1000;

    public static void validateProjectDto(ProjectDto projectDto) throws ValidationException {
        validateFieldsLength(projectDto.getName(), projectDto.getDescription());
    }

    public static void validateTaskDto(TaskDto taskDto) throws ValidationException {
        validateFieldsLength(taskDto.getTaskName(), taskDto.getTaskDescription());
    }

    private static void validateFieldsLength(String name, String description) throws ValidationException {
        if (name == null || description == null) {
            throw new ValidationException();
        }
        if (name.length() > NAME_LENGTH || description.length() > DESCRIPTION_LENGTH) {
            throw new ValidationException();
        }
    }
}