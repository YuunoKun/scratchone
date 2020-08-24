package me.scratchone.service;

import me.scratchone.domain.Article;
import me.scratchone.domain.PageBean;

public interface ArticleService {

    PageBean<Article> pageQuery(int currentPage, int pageSize);

    Article findArticle(int aid);
}
