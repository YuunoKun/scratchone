package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;
import me.scratchone.domain.School;
import me.scratchone.domain.User;
import me.scratchone.service.SchoolService;
import me.scratchone.service.UserService;
import me.scratchone.service.impl.SchoolServiceImpl;
import me.scratchone.service.impl.UserServiceImpl;
import me.scratchone.util.JedisUtil;
import me.scratchone.util.SerializeUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profileQueryServlet")
public class ProfileQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String token = null;
//        for (Cookie cookie : req.getCookies()) {
//            if(cookie.getName().equals("so_token")) {
//                token = cookie.getValue();
//            }
//        }
        token = req.getHeader("so_token");

        User user = null;
        ResultInfo resultInfo = new ResultInfo();
        Jedis jedis = JedisUtil.getJedis();

        if(token == null || token.equals("null")) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Server error: Login info invalid.");
        } else {
            try {
                byte[] u = jedis.get(token.getBytes());
                user = (User) SerializeUtil.deserialize(u);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                // log
            }

            if(user == null) {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("Server error: Profile not found.");
            } else {
                user.setStatus("");
                user.setCode("");
                user.setPassword("");

                SchoolService schoolService = new SchoolServiceImpl();
                School school = schoolService.queryForSchool(user.getSid());

                resultInfo.setFlag(true);
                resultInfo.setData(user);
                resultInfo.setErrorMsg(school.getName());
            }
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
