package by.tech.project_management_app.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {
    List<String> getRolesById(int userId);
}