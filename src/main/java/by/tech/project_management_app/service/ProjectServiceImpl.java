package by.tech.project_management_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.tech.project_management_app.dto.ProjectDto;
import by.tech.project_management_app.entities.Project;
import by.tech.project_management_app.repository.ProjectRepository;
import by.tech.project_management_app.repository.TaskRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final int OFFSET = 10;
    private static final boolean IS_NOT_DELETED = true;
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Project> getAllFromPage(int page, int pageSize, String order, String projectName, int createdBy) {
        int offset = 0;
        if (page > 1) {
            offset = (page - 1) * OFFSET;
        }
        if (projectName != null) {
            projectName = "%" + projectName + "%";
        }
        return projectRepository.findAllByParameters(pageSize, offset, order, projectName, createdBy);
    }

    @Override
    public Project findById(int id) throws ServiceException {
        try {
            return projectRepository.findById(id);
        } catch (Exception ex) {
            throw new ServiceException("Project is not found");
        }
    }

    @Override
    public Project create(ProjectDto projectDto, int userId) throws ValidationException {
        Validator.validateProjectDto(projectDto);
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setCreatedBy(userId);
        project.setModifiedBy(userId);
        project.setDeleted(IS_NOT_DELETED);
        return projectRepository.create(project);
    }

    @Override
    public void edit(ProjectDto projectDto, int id, int userId) throws ServiceException, ValidationException {
        Validator.validateProjectDto(projectDto);
        try {
            checkIfProjectExists(id);
        } catch (Exception ex) {
            throw new ServiceException("Project is not found");
        }
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setModifiedBy(userId);
        project.setId(id);
        projectRepository.update(project);
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            checkIfProjectExists(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Project is not found");
        }
        projectRepository.deleteById(id);
    }

    public void checkIfProjectExists(int id) throws ServiceException {
        int countProject = projectRepository.countProjectId(id);
        if (countProject == 0) {
            throw new ServiceException();
        }
    }
}