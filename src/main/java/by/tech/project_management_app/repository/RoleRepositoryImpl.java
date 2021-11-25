package by.tech.project_management_app.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private static final String SELECT_ROLES_BY_USER = "SELECT authorities.authority "
            + "FROM authorities JOIN users_authorities ON users_authorities.authority_id = authorities.id "
            + "WHERE users_authorities.user_id = :id";
    private static final String ID = "id";
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<String> getRolesById(int userId) {
        SqlParameterSource parameters = new MapSqlParameterSource(ID, userId);
        return namedParameterJdbcTemplate.queryForList(SELECT_ROLES_BY_USER, parameters, String.class);
    }
}