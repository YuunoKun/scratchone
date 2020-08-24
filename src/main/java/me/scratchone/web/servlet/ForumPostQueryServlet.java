package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ForumPost;
import me.scratchone.domain.ResultInfo;
import me.scratchone.service.ForumService;
import me.scratchone.service.impl.ForumServiceImpl;
import sun.reflect.annotation.ExceptionProxy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/forumPostQueryServlet")
public class ForumPostQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ForumPost> posts = null;
        ForumService forumService = new ForumServiceImpl();
        ResultInfo resultInfo = new ResultInfo();

        try{
            posts = forumService.getAllForumPost();
            for(ForumPost post: posts) {
                post.setParseReleaseDate(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(post.getReleaseDate()));
            }

            resultInfo.setFlag(true);
            resultInfo.setData(posts);
        } catch(Exception e) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Servlet error. Query failed.");
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
