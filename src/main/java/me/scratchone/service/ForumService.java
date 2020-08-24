package me.scratchone.service;

import me.scratchone.domain.ForumPost;
import me.scratchone.domain.ForumReply;

import java.util.List;

public interface ForumService {
    void createForumPost(ForumPost forumPost);

    void deleteForumPost(int pid);

    List<ForumPost> getAllForumPost();

    int getForumPostCount();

    List<ForumReply> getAllForumReply(int pid);

    void deleteForumReply(int pid, int rid);

    void addForumReply(ForumReply forumReply);

    int getForumPostReplyCount(int pid);
}
