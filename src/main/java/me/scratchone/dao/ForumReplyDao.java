package me.scratchone.dao;

import me.scratchone.domain.ForumReply;

import java.util.List;

public interface ForumReplyDao {

    void addReply(ForumReply forumReply);

    void deleteReply(int pid, int rid);

    void deleteAllReplyByPid(int pid);

    List<ForumReply> getReplyByPid(int pid);
}
