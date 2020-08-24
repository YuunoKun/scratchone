package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;
import me.scratchone.domain.User;
import me.scratchone.service.UserService;
import me.scratchone.service.impl.UserServiceImpl;
import me.scratchone.util.UserCensorUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/postProfileQueryServlet")
public class PostProfileQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int uid = Integer.parseInt(req.getParameter("post_uid"));

        ResultInfo resultInfo = new ResultInfo();

        try {
            UserService userService = new UserServiceImpl();
            User user = userService.getUserProfileByUid(uid);

            resultInfo.setFlag(true);
            resultInfo.setData(UserCensorUtil.censor(user));
        } catch (Exception e) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Server Error.");
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
