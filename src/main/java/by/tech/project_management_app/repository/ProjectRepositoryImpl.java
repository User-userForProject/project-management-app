package by.tech.project_management_app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import by.tech.project_management_app.entities.Project;
import by.tech.project_management_app.entities.ProjectMapper;
import by.tech.project_management_app.entities.Task;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED_BY = "createdBy";
    private static final String MODIFIED_BY = "modifiedBy";
    private static final String IS_DELETED = "isDeleted";
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM projects";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM projects WHERE id = :id";
    private static final String SELECT_PROJECTS_WITH_TASKS__QUERY = "SELECT p.id, p.name, p.description, p.createdBy,"
            + " p.modifiedBy, p.isDeleted, t.id as taskId, t.name as taskName," + " t.description as taskDescription,"
            + " t.createdBy as taskCreatedBy, t.modifiedBy as taskModifiedBy, t.isDeleted as taskIsDeleted,"
            + " t.isDone FROM (%s) AS p" + " LEFT JOIN tasks AS t" + " ON p.id = t.project_id";
    private static final String SELECT_ALL_AVAILABLE_QUERY = "SELECT * FROM projects WHERE isDeleted = 0";
    private static final String CREATED_BY_CLAUSE = " AND createdBy = :createdBy";
    private static final String NAME_CLAUSE = " AND name LIKE :name";
    private static final String ORDER_DESC = " ORDER BY ID DESC";
    private static final String LIMIT_CLAUSE = " LIMIT :limit OFFSET :offset";

    private static final String INSERT_QUERY = "INSERT INTO projects (name, description, createdBy, modifiedBy, isDeleted) VALUES (:name, :description, :createdBy, :modifiedBy, :isDeleted)";
    private static final String UPDATE_QUERY = "UPDATE projects SET name = :name, description = :description, modifiedBy = :modifiedBy WHERE id=:id";
    private static final String SOFT_DELETE_QUERY = "UPDATE projects SET isdeleted = 1 WHERE id = :id";
    private static final String COUNT_ID_QUERY = "SELECT COUNT(id) FROM projects WHERE id = :id";

    private static final int IS_NOT_DELETED = 0;
    private static final String DESC = "desc";
    private ProjectMapper projectMapper = new ProjectMapper();
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Project> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, projectMapper);
    }

    @Override
    public List<Project> findAllByParameters(int limit, int offset, String sort, String projectName, int createdBy) {
        StringBuilder statement = new StringBuilder(SELECT_ALL_AVAILABLE_QUERY);
        Map<String, Object> paramMap = new HashMap<>();
        if (createdBy != 0) {
            statement.append(CREATED_BY_CLAUSE);
            paramMap.put(CREATED_BY, createdBy);
        }
        if (projectName != null) {
            statement.append(NAME_CLAUSE);
            paramMap.put(NAME, projectName);
        }
        if (sort.equals(DESC)) {
            statement.append(ORDER_DESC);
        }
        statement.append(LIMIT_CLAUSE);
        paramMap.put(LIMIT, limit);
        paramMap.put(OFFSET, offset);
        return namedParameterJdbcTemplate.query(String.format(SELECT_PROJECTS_WITH_TASKS__QUERY, statement.toString()),
                paramMap, new ProjectWithTasksExtractor());
    }

    @Override
    public void deleteById(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        namedParameterJdbcTemplate.update(SOFT_DELETE_QUERY, parameters);
    }

    public int countProjectId(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.queryForObject(COUNT_ID_QUERY, parameters, Integer.class);
    }

    @Override
    public Project create(Project project) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(NAME, project.getName());
        paramMap.put(DESCRIPTION, project.getDescription());
        paramMap.put(CREATED_BY, project.getCreatedBy());
        paramMap.put(MODIFIED_BY, project.getModifiedBy());
        paramMap.put(IS_DELETED, IS_NOT_DELETED);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlUpdate insertProject = new SqlUpdate(dataSource, INSERT_QUERY);
        insertProject.declareParameter(new SqlParameter(NAME, Types.VARCHAR));
        insertProject.declareParameter(new SqlParameter(DESCRIPTION, Types.VARCHAR));
        insertProject.declareParameter(new SqlParameter(CREATED_BY, Types.INTEGER));
        insertProject.declareParameter(new SqlParameter(MODIFIED_BY, Types.INTEGER));
        insertProject.declareParameter(new SqlParameter(IS_DELETED, Types.INTEGER));
        insertProject.setGeneratedKeysColumnNames(new String[] { ID });
        insertProject.setReturnGeneratedKeys(true);
        insertProject.updateByNamedParam(paramMap, keyHolder);
        project.setId(keyHolder.getKey().intValue());
        return project;
    }

    public void update(Project project) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(NAME, project.getName());
        paramMap.put(DESCRIPTION, project.getDescription());
        paramMap.put(MODIFIED_BY, project.getModifiedBy());
        paramMap.put(ID, project.getId());
        SqlUpdate updateProject = new SqlUpdate(dataSource, UPDATE_QUERY);
        updateProject.declareParameter(new SqlParameter(ID, Types.INTEGER));
        updateProject.declareParameter(new SqlParameter(DESCRIPTION, Types.VARCHAR));
        updateProject.declareParameter(new SqlParameter(NAME, Types.VARCHAR));
        updateProject.declareParameter(new SqlParameter(MODIFIED_BY, Types.INTEGER));
        updateProject.updateByNamedParam(paramMap);
    }

    @Override
    public Project findById(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.query(String.format(SELECT_PROJECTS_WITH_TASKS__QUERY, FIND_BY_ID_QUERY),
                parameters, new ProjectWithTasksExtractor()).stream().findAny().get();
    }

    private static final class ProjectWithTasksExtractor implements ResultSetExtractor<List<Project>> {
        private static final String TASK_ID = "taskId";
        private static final String TASK_NAME = "taskName";
        private static final String TASK_DESCRIPTION = "taskDescription";
        private static final String TASK_CREATED_BY = "taskCreatedBy";
        private static final String TASK_MODIFIED_BY = "taskModifiedBy";
        private static final String IS_DONE = "isDone";

        @Override
        public List<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, Project> map = new HashMap<>();
            Project project;
            while (rs.next()) {
                Integer id = rs.getInt(ID);
                project = map.get(id);
                if (project == null) {
                    project = new Project();
                    project.setId(id);
                    project.setName(rs.getString(NAME));
                    project.setDescription(rs.getString(DESCRIPTION));
                    project.setCreatedBy(rs.getInt(CREATED_BY));
                    project.setModifiedBy(rs.getInt(MODIFIED_BY));
                    project.setDeleted(rs.getInt(IS_DELETED) == 1 ? true : false);
                    project.setTasks(new ArrayList<>());
                    map.put(id, project);
                }
                Integer taskId = rs.getInt(TASK_ID);
                if (taskId > 0) {
                    Task task = new Task();
                    task.setId(taskId);
                    task.setProjectId(id);
                    task.setTaskName(rs.getString(TASK_NAME));
                    task.setTaskDescription(rs.getString(TASK_DESCRIPTION));
                    task.setCreatedBy(rs.getInt(TASK_CREATED_BY));
                    task.setModifiedBy(rs.getInt(TASK_MODIFIED_BY));
                    task.setDeleted(rs.getInt(IS_DELETED) == 1 ? true : false);
                    task.setDone(rs.getInt(IS_DONE) == 1 ? true : false);
                    project.addTask(task);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

}