package me.scratchone.service.impl;

import me.scratchone.dao.ForumPostDao;
import me.scratchone.dao.ForumReplyDao;
import me.scratchone.dao.impl.ForumPostDaoImpl;
import me.scratchone.dao.impl.ForumReplyDaoImpl;
import me.scratchone.domain.ForumPost;
import me.scratchone.domain.ForumReply;
import me.scratchone.service.ForumService;

import java.text.SimpleDateFormat;
import java.util.List;

public class ForumServiceImpl implements ForumService {

    private ForumPostDao forumPostDao = new ForumPostDaoImpl();
    private ForumReplyDao forumReplyDao = new ForumReplyDaoImpl();

    @Override
    public void createForumPost(ForumPost forumPost) {
        forumPostDao.createPost(forumPost);
    }

    @Override
    public void deleteForumPost(int pid) {
        forumReplyDao.deleteAllReplyByPid(pid);
        forumPostDao.deletePost(pid);
    }

    @Override
    public List<ForumPost> getAllForumPost() {

        List<ForumPost> list = forumPostDao.findAllPost();

        for(ForumPost fp: list) {
            fp.setParseReleaseDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fp.getReleaseDate()));
        }

        return list;
    }

    @Override
    public int getForumPostCount() { return forumPostDao.findPostCount(); }

    @Override
    public List<ForumReply> getAllForumReply(int pid) {
        List<ForumReply> list = forumReplyDao.getReplyByPid(pid);

        for(ForumReply fr: list) {
            fr.setParseReleaseDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fr.getReleaseDate()));
        }

        return list;
    }

    @Override
    public void deleteForumReply(int pid, int rid) { forumReplyDao.deleteReply(pid, rid); }

    @Override
    public void addForumReply(ForumReply forumReply) { forumReplyDao.addReply(forumReply); }

    @Override
    public int getForumPostReplyCount(int pid) { return forumPostDao.findReplyCountByPid(pid); }
}
