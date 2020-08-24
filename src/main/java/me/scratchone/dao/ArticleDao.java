package me.scratchone.dao;

import me.scratchone.domain.Article;

import java.util.List;

public interface ArticleDao {

    int findTotalCount();

    List<Article> getArticleByPage(int start, int pageSize);

    Article findArticleByAid(int aid);
}
