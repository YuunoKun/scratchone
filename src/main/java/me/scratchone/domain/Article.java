package me.scratchone.domain;

import java.util.Date;

public class Article {

    private int aid;
    private String title;
    private String author;
    private String content;
    private Date release;

    private ArticleImg image;

    public int getAid() { return aid; }

    public void setAid(int aid) { this.aid = aid; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public Date getRelease() { return release; }

    public void setRelease(Date release) { this.release = release; }

    public ArticleImg getImage() { return image; }

    public void setImage(ArticleImg image) { this.image = image; }
}
