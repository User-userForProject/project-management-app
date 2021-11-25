package by.tech.project_management_app.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProjectMapper implements RowMapper<Project> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED_BY = "createdBy";
    private static final String MODIFIED_BY = "modifiedBy";
    private static final String IS_DELETED = "isDeleted";


    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt(ID));
        project.setName(rs.getString(NAME));
        project.setDescription(rs.getString(DESCRIPTION));
        project.setCreatedBy(rs.getInt(CREATED_BY));
        project.setCreatedBy(rs.getInt(MODIFIED_BY));
        project.setDeleted(rs.getInt(IS_DELETED) == 1 ? true : false);
        return project;
    }

}