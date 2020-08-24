package me.scratchone.dao.impl;

import me.scratchone.dao.ForumPostDao;
import me.scratchone.domain.ForumPost;
import me.scratchone.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ForumPostDaoImpl implements ForumPostDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findPostCount() {
        String sql = "SELECT count(*) FROM so_forum_post";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public int findReplyCountByPid(int pid) {
        String sql = "SELECT (reply) FROM so_forum_post WHERE pid = ?";
        return template.queryForObject(sql, Integer.class, pid);
    }

    @Override
    public ForumPost findPostByPid(int pid) {
        ForumPost forumPost = null;

        try {
            String sql = "SELECT * FROM so_forum_post where pid = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<ForumPost>(ForumPost.class), pid);
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }
        return forumPost;
    }

    @Override
    public List<ForumPost> findAllPost() {
        List<ForumPost> list = null;

        try {
            String sql = "SELECT * FROM so_forum_post";
            list = template.query(sql, new BeanPropertyRowMapper<ForumPost>(ForumPost.class));
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ForumPost> findPostByPage(int currentPage, int pageSize) {
        List<ForumPost> list = null;

        int start = (currentPage - 1) * pageSize;
        int end = start + pageSize;

        try {
            String sql = "SELECT * FROM so_forum_post limit ? , ?";
            list = template.query(sql, new BeanPropertyRowMapper<ForumPost>(ForumPost.class), start, end);
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void createPost(ForumPost post) {
        String sql = "insert into so_forum_post(pid,uid,title,releasedate,link) values(?,?,?,?,?)";

        try {
            template.update(sql,post.getPid(),
                    post.getUid(),
                    post.getTitle(),
                    post.getReleaseDate(),
                    post.getLink());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePost(int pid) {
        String sql = "delete from so_forum_post where pid = ?";
        template.update(sql, pid);
    }
}
