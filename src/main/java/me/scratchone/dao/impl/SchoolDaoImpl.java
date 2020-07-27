package me.scratchone.dao.impl;

import me.scratchone.dao.SchoolDao;
import me.scratchone.domain.School;
import me.scratchone.domain.User;
import me.scratchone.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SchoolDaoImpl implements SchoolDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<School> findAllSchools() {
        List<School> schools = null;
        try {
            String sql = "SELECT * FROM so_school";
            schools = template.query(sql, new BeanPropertyRowMapper<School>(School.class));
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }
        return schools;
    }

    @Override
    public List<School> findSchoolByType(String type) {
        List<School> schools = null;
        try {
            String sql = "SELECT * FROM so_school WHERE tid = (SELECT tid FROM so_school_type WHERE name = ?)";
            schools = template.query(sql, new BeanPropertyRowMapper<School>(School.class), type);
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }
        return schools;
    }

    @Override
    public School findSchoolByName(String name) {
        School school = null;
        try {
            String sql = "SELECT * FROM so_school WHERE name = ?";
            school = template.queryForObject(sql, new BeanPropertyRowMapper<School>(School.class), name);
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }
        return school;
    }

    @Override
    public School findSchoolBySid(int sid) {
        School school = null;
        try {
            String sql = "SELECT * FROM so_school WHERE sid = ?";
            school = template.queryForObject(sql, new BeanPropertyRowMapper<School>(School.class), sid);
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }
        return school;
    }
}
