package me.scratchone.dao.impl;

import me.scratchone.dao.SchoolTypeDao;
import me.scratchone.domain.SchoolType;
import me.scratchone.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SchoolTypeDaoImpl implements SchoolTypeDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<SchoolType> getAll() {
        List<SchoolType> schoolTypes = null;
        try {
            String sql = "SELECT * FROM so_school_type";
            schoolTypes = template.query(sql, new BeanPropertyRowMapper<SchoolType>(SchoolType.class));
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }
        return schoolTypes;
    }
}
