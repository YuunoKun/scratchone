package me.scratchone.domain;

import java.util.Date;

public class ForumPost {

    private int pid;
    private int uid;
    private String title;
    private Date releaseDate;
    private String link;
    private int reply;

    private String parseReleaseDate;

    public int getPid() { return pid; }

    public void setPid(int pid) { this.pid = pid; }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Date getReleaseDate() { return releaseDate; }

    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public int getReply() { return reply; }

    public void setReply(int reply) { this.reply = reply; }

    public String getParseReleaseDate() { return parseReleaseDate; }

    public void setParseReleaseDate(String parseReleaseDate) { this.parseReleaseDate = parseReleaseDate; }
}
