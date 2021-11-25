package by.tech.project_management_app.repository;

import java.util.List;

import by.tech.project_management_app.entities.Project;

public interface ProjectRepository{
    Project create(Project project);
    Project findById(int id);
    void update(Project project);
    void deleteById(int id);
    List<Project> findAll();
    List<Project> findAllByParameters(int limit, int offset, String sort, String projectName, int createdBy);
    int countProjectId(int id);
}