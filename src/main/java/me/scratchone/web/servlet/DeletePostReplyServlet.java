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
import java.util.List;

@WebServlet("/deletePostReplyServlet")
public class DeletePostReplyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pid = Integer.parseInt(req.getHeader("Referer").split("fp-")[1].split(".html")[0]);
        int rid = Integer.parseInt(req.getParameter("reply_rid"));

        ForumService forumService = new ForumServiceImpl();
        ResultInfo resultInfo = new ResultInfo();

        try{
            forumService.deleteForumReply(pid, rid);

            resultInfo.setFlag(true);
        } catch(Exception e) {
            e.printStackTrace();

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Server error. Failed to delete reply.");
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
