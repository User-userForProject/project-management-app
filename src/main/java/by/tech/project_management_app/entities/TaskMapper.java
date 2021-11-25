package by.tech.project_management_app.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TaskMapper implements RowMapper<Task> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED_BY = "createdBy";
    private static final String MODIFIED_BY = "modifiedBy";
    private static final String IS_DELETED = "isDeleted";
    private static final String IS_DONE = "isDone";
    private static final String PROJECT_ID = "project_id";

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt(ID));
        task.setTaskName(rs.getString(NAME));
        task.setTaskDescription(rs.getString(DESCRIPTION));
        task.setCreatedBy(rs.getInt(CREATED_BY));
        task.setModifiedBy(rs.getInt(MODIFIED_BY));
        task.setDeleted(rs.getInt(IS_DELETED)==1 ? true : false);
        task.setDone(rs.getInt(IS_DONE) == 1 ? true : false);
        task.setProjectId(rs.getInt(PROJECT_ID));
        return task;
    }

}