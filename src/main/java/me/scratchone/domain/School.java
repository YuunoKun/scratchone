package me.scratchone.domain;

import java.io.Serializable;

public class School implements Serializable {

    private int sid;
    private String name;
    private int tid;

    public int getSid() { return sid; }

    public void setSid(int sid) { this.sid = sid; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getTid() { return tid; }

    public void setTid(int tid) { this.tid = tid; }
}
