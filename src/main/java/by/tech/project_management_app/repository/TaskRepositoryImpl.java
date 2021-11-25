package by.tech.project_management_app.repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import by.tech.project_management_app.entities.Task;
import by.tech.project_management_app.entities.TaskMapper;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private static final String SELECT_TASK_BY_ID_QUERY = "SELECT id, name, description, createdBy, modifiedBy,"
            + " isDeleted, isDone, project_id" + " FROM tasks where id = :id AND isDeleted = 0";
    private static final String COUNT_ID_QUERY = "SELECT COUNT(id) FROM tasks WHERE id = :id";
    private static final String SOFT_DELETE_QUERY = "UPDATE tasks SET isdeleted = 1 WHERE id = :id";
    private static final String INSERT_QUERY = "INSERT INTO tasks (name, description, createdBy, modifiedBy, isDeleted, isDone, project_id) VALUES (:name, :description, :createdBy, :modifiedBy, :isDeleted, :isDone, :projectId)";
    private static final String UPDATE_QUERY = "UPDATE tasks SET name = :name, description = :description, modifiedBy = :modifiedBy, isDone = :isDone WHERE id=:id";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED_BY = "createdBy";
    private static final String MODIFIED_BY = "modifiedBy";
    private static final String IS_DELETED = "isDeleted";
    private static final String IS_DONE = "isDone";
    private static final String PROJECT_ID = "projectId";
    private static final int IS_NOT_DELETED = 0;
    private static final int DONE = 0;
    private TaskMapper taskMapper = new TaskMapper();
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Task create(Task task) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(NAME, task.getTaskName());
        paramMap.put(DESCRIPTION, task.getTaskDescription());
        paramMap.put(CREATED_BY, task.getCreatedBy());
        paramMap.put(MODIFIED_BY, task.getModifiedBy());
        paramMap.put(IS_DELETED, IS_NOT_DELETED);
        paramMap.put(IS_DONE, DONE);
        paramMap.put(PROJECT_ID, task.getProjectId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlUpdate insertTask = new SqlUpdate(dataSource, INSERT_QUERY);
        insertTask.declareParameter(new SqlParameter(NAME, Types.VARCHAR));
        insertTask.declareParameter(new SqlParameter(DESCRIPTION, Types.VARCHAR));
        insertTask.declareParameter(new SqlParameter(CREATED_BY, Types.INTEGER));
        insertTask.declareParameter(new SqlParameter(MODIFIED_BY, Types.INTEGER));
        insertTask.declareParameter(new SqlParameter(IS_DELETED, Types.INTEGER));
        insertTask.declareParameter(new SqlParameter(IS_DONE, Types.INTEGER));
        insertTask.declareParameter(new SqlParameter(PROJECT_ID, Types.INTEGER));
        insertTask.setGeneratedKeysColumnNames(new String[] { ID });
        insertTask.setReturnGeneratedKeys(true);
        insertTask.updateByNamedParam(paramMap, keyHolder);
        task.setId(keyHolder.getKey().intValue());
        return task;
    }

    @Override
    public Task findById(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.query(SELECT_TASK_BY_ID_QUERY, parameters, taskMapper).get(0);
    }

    @Override
    public void update(Task task) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(NAME, task.getTaskName());
        paramMap.put(DESCRIPTION, task.getTaskDescription());
        paramMap.put(MODIFIED_BY, task.getModifiedBy());
        paramMap.put(IS_DONE, task.isDone() ? 0 : 1);
        paramMap.put(ID, task.getId());
        SqlUpdate updateTask = new SqlUpdate(dataSource, UPDATE_QUERY);
        updateTask.declareParameter(new SqlParameter(ID, Types.INTEGER));
        updateTask.declareParameter(new SqlParameter(DESCRIPTION, Types.VARCHAR));
        updateTask.declareParameter(new SqlParameter(NAME, Types.VARCHAR));
        updateTask.declareParameter(new SqlParameter(MODIFIED_BY, Types.INTEGER));
        updateTask.declareParameter(new SqlParameter(IS_DONE, Types.INTEGER));
        updateTask.updateByNamedParam(paramMap);
    }

    @Override
    public void deleteById(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        namedParameterJdbcTemplate.update(SOFT_DELETE_QUERY, parameters);
    }

    @Override
    public int countTaskId(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.queryForObject(COUNT_ID_QUERY, parameters, Integer.class);
    }

}