package me.scratchone.dao.impl;

import me.scratchone.dao.ForumReplyDao;
import me.scratchone.domain.ForumPost;
import me.scratchone.domain.ForumReply;
import me.scratchone.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ForumReplyDaoImpl implements ForumReplyDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void addReply(ForumReply forumReply) {
        String sql = "INSERT INTO so_forum_reply(pid,rid,uid,releaseDate,reply) value(?,?,?,?,?)";
        template.update(sql,
                forumReply.getPid(),
                forumReply.getRid(),
                forumReply.getUid(),
                forumReply.getReleaseDate(),
                forumReply.getReply());

        String sql1 = "SELECT (reply) FROM so_forum_post WHERE pid = ?";
        int replies = template.queryForObject(sql1, Integer.class, forumReply.getPid());

        String sql2 = "UPDATE so_forum_post SET reply = ? WHERE pid = ?";
        template.update(sql2, replies + 1, forumReply.getPid());
    }

    @Override
    public void deleteReply(int pid, int rid) {
        String sql = "DELETE FROM so_forum_reply WHERE pid = ? AND rid = ?";
        template.update(sql, pid, rid);
    }

    @Override
    public void deleteAllReplyByPid(int pid) {
        String sql = "DELETE FROM so_forum_reply WHERE pid = ?";
        template.update(sql, pid);
    }

    @Override
    public List<ForumReply> getReplyByPid(int pid) {
        List<ForumReply> list = null;

        try {
            String sql = "SELECT * FROM so_forum_reply WHERE pid = ?";
            list = template.query(sql, new BeanPropertyRowMapper<ForumReply>(ForumReply.class), pid);
        } catch(EmptyResultDataAccessException ignored) {

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
