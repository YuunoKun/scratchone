package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ForumReply;
import me.scratchone.domain.ResultInfo;
import me.scratchone.service.ForumService;
import me.scratchone.service.impl.ForumServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/addPostReplyServlet")
public class AddPostReplyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ForumService forumService = new ForumServiceImpl();
        ResultInfo resultInfo = new ResultInfo();

        try{
            String reply = req.getParameter("reply_reply");
            int uid = Integer.parseInt(req.getParameter("reply_uid"));
            int pid = Integer.parseInt(req.getHeader("Referer").split("fp-")[1].split(".html")[0]);
            int rid = forumService.getForumPostReplyCount(pid) + 1;
            Date releaseDate = new Date();

            ForumReply forumReply = new ForumReply();
            forumReply.setPid(pid);
            forumReply.setRid(rid);
            forumReply.setUid(uid);
            forumReply.setReleaseDate(releaseDate);
            forumReply.setReply(reply);

            forumService.addForumReply(forumReply);

            resultInfo.setFlag(true);
            resultInfo.setData(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(forumReply.getReleaseDate()));
            resultInfo.setErrorMsg(""+rid);
        } catch(Exception e) {
            e.printStackTrace();

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Server error. Failed to add reply");
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
