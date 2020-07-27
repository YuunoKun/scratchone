package me.scratchone.dao.impl;

import me.scratchone.dao.UserDao;
import me.scratchone.domain.User;
import me.scratchone.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try {
            String sql = "select * from so_user where email = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try {
            String sql = "select * from so_user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch(EmptyResultDataAccessException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from so_user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch(EmptyResultDataAccessException ignored) {

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void createNewUser(User user) {
        String sql = "insert into so_user(username,password,name,email,status,code,sid) values(?,?,?,?,?,?,?)";

        try {
            template.update(sql,user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getCode(),
                    user.getSid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserStatusByCode(String code) {
        try {
            String sql = "UPDATE so_user SET status = 'Y' WHERE code = ?";
            template.update(sql, code);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserByProfile(User user) {
        try {
            String sql = "UPDATE so_user SET name = ? ,sid = ? ,target_school = ? ,target_major = ? WHERE uid = ?";
            template.update(sql, user.getName(), user.getSid(), user.getTarget_school(), user.getTarget_major(), user.getUid());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
