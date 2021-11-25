package by.tech.project_management_app.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import by.tech.project_management_app.entities.User;
import by.tech.project_management_app.entities.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SELECT_USER_BY_NAME_QUERY = "SELECT id, username, password, enabled FROM users where username = :username";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT id, username, password, enabled FROM users where id = :id";
    private static final String SELECT_ALL_QUERY = "SELECT id, username, password, enabled FROM users";
    private static final String SOFT_DELETE_QUERY = "UPDATE users SET enabled = 0 WHERE id = :id";
    private static final String COUNT_ID_QUERY = "SELECT COUNT(id) FROM users WHERE id = :id";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private UserMapper userMapper = new UserMapper();
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User findUserById(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.query(SELECT_USER_BY_ID_QUERY, parameters, userMapper).get(0);
    }

    @Override
    public User findByUsername(String userName) {
        SqlParameterSource parameters = new MapSqlParameterSource(USERNAME, userName);
        return namedParameterJdbcTemplate.query(SELECT_USER_BY_NAME_QUERY, parameters, userMapper).get(0);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, userMapper);
    }

    @Override
    public void deleteUser(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        namedParameterJdbcTemplate.update(SOFT_DELETE_QUERY, parameters);
    }

    public int countUserId(int id) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, id);
        return namedParameterJdbcTemplate.queryForObject(COUNT_ID_QUERY, parameters, Integer.class);
    }
}