package by.tech.project_management_app.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import by.tech.project_management_app.dto.ProjectDto;
import by.tech.project_management_app.entities.Project;
import by.tech.project_management_app.entities.SecurityUser;
import by.tech.project_management_app.service.ProjectService;
import by.tech.project_management_app.service.ServiceException;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public List<Project> listData(@RequestParam(defaultValue = "0") int createdBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false) String project, @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int page) {
        return projectService.getAllFromPage(page, pageSize, order, project, createdBy);
    }

    @GetMapping("/{id}")
    public Project findProjectById(@PathVariable int id) {
        try {
            return projectService.findById(id);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project is not found", ex);
        }
    }

    @PostMapping()
    public Project createProject(@RequestBody ProjectDto projectDto) {
        int userId = getUserIdFromAuthentication();
        try {
            return projectService.create(projectDto, userId);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project is not created. One or more fileds are invalid", ex);
        }
    }

    @PatchMapping("/{id}")
    public void editProject(@RequestBody ProjectDto projectDto, @PathVariable int id) {
        int userId = getUserIdFromAuthentication();
        try {
            projectService.edit(projectDto, id, userId);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with provided id is not found", ex);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            projectService.delete(id);
        } catch (ServiceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with provided id is not found", ex);
        }
    }

    private int getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.getUserId();
    }
}