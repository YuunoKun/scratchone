package me.scratchone.domain;

import java.util.Date;

public class ForumReply {

    private int rpid;
    private int pid;
    private int rid;
    private int uid;
    private Date releaseDate;
    private String reply;

    private String parseReleaseDate;


    public int getRpid() { return rpid; }

    public void setRpid(int rpid) { this.rpid = rpid; }

    public int getPid() { return pid; }

    public void setPid(int pid) { this.pid = pid; }

    public int getRid() { return rid; }

    public void setRid(int rid) { this.rid = rid; }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    public Date getReleaseDate() { return releaseDate; }

    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

    public String getReply() { return reply; }

    public void setReply(String reply) { this.reply = reply; }

    public String getParseReleaseDate() { return parseReleaseDate; }

    public void setParseReleaseDate(String parseReleaseDate) { this.parseReleaseDate = parseReleaseDate; }

}
