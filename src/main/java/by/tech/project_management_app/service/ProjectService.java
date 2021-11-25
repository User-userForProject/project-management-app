package by.tech.project_management_app.service;

import java.util.List;

import by.tech.project_management_app.dto.ProjectDto;
import by.tech.project_management_app.entities.Project;

public interface ProjectService {

    Project findById(int id) throws ServiceException;

    void edit(ProjectDto projectDto, int id, int userId) throws ServiceException, ValidationException;

    void delete(int id) throws ServiceException;

    Project create(ProjectDto projectDto, int userId) throws ValidationException;

    List<Project> getAllFromPage(int page, int pageSize, String order, String projectName, int createdBy);

    void checkIfProjectExists(int id) throws ServiceException;
}