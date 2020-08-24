package me.scratchone.service.impl;

import me.scratchone.dao.ArticleDao;
import me.scratchone.dao.ArticleImgDao;
import me.scratchone.dao.impl.ArticleDaoImpl;
import me.scratchone.dao.impl.ArticleImgDaoImpl;
import me.scratchone.domain.Article;
import me.scratchone.domain.ArticleImg;
import me.scratchone.domain.PageBean;
import me.scratchone.service.ArticleService;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao = new ArticleDaoImpl();

    private ArticleImgDao articleImgDao = new ArticleImgDaoImpl();

    @Override
    public PageBean<Article> pageQuery(int currentPage, int pageSize) {

        PageBean<Article> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);

        int totalCount = articleDao.findTotalCount();
        pb.setTotalCount(totalCount);

        int start = (currentPage - 1) * pageSize;
        List<Article> list = articleDao.getArticleByPage(start,pageSize);
        pb.setList(list);

        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1 ;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Article findArticle(int aid) {
        Article article = articleDao.findArticleByAid(aid);
        ArticleImg articleImg = articleImgDao.findByAid(aid);
        article.setImage(articleImg);

        return article;
    }
}
