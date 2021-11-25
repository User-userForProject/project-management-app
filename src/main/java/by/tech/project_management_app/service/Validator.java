package by.tech.project_management_app.service;

import by.tech.project_management_app.dto.ProjectDto;
import by.tech.project_management_app.dto.TaskDto;

public class Validator {
    private static final int NAME_LENGTH = 45;
    private static final int DESCRIPTION_LENGTH = 1000;

    public static boolean validateProjectDto(ProjectDto projectDto) {
        return validateFieldsLength(projectDto.getName(), projectDto.getDescription());
    }

    public static boolean validateTaskDto(TaskDto taskDto) {
        return validateFieldsLength(taskDto.getTaskName(), taskDto.getTaskDescription());
    }

    private static boolean validateFieldsLength(String name, String description) {
        if (name.length() > NAME_LENGTH || description.length() > DESCRIPTION_LENGTH) {
            return false;
        } else {
            return true;
        }
    }
}