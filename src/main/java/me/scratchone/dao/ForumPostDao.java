package me.scratchone.dao;

import me.scratchone.domain.ForumPost;

import java.util.List;

public interface ForumPostDao {

    int findPostCount();

    int findReplyCountByPid(int pid);

    ForumPost findPostByPid(int pid);

    List<ForumPost> findAllPost();

    List<ForumPost> findPostByPage(int currentPage, int pageSize);

    void createPost(ForumPost post);

    void deletePost(int pid);
}
