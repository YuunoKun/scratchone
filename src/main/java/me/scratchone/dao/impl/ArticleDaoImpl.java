package me.scratchone.dao.impl;

import me.scratchone.dao.ArticleDao;
import me.scratchone.domain.Article;
import me.scratchone.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ArticleDaoImpl implements ArticleDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount() {
        String sql = "select count(*) from so_article";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Article> getArticleByPage(int start, int pageSize) {
        String sql = "select * from so_article limit ? , ?";
        return template.query(sql,new BeanPropertyRowMapper<Article>(Article.class),start, start + pageSize);
    }

    @Override
    public Article findArticleByAid(int aid) {
        String sql = "select * from so_article where aid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Article>(Article.class), aid);
    }
}
