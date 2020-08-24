package me.scratchone.dao.impl;

import me.scratchone.dao.ArticleImgDao;
import me.scratchone.domain.ArticleImg;
import me.scratchone.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ArticleImgDaoImpl implements ArticleImgDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public ArticleImg findByAid(int aid) {
        String sql = "select * from so_article_img where aid = ? ";
        return template.queryForObject(sql,new BeanPropertyRowMapper<ArticleImg>(ArticleImg.class),aid);
    }
}
