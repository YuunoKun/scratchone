package me.scratchone.domain;

import java.io.Serializable;

public class ArticleImg implements Serializable {

    private int aid;
    private String image;

    public int getAid() { return aid; }

    public void setAid(int aid) { this.aid = aid; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
