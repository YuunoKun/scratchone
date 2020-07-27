package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;
import me.scratchone.domain.User;
import me.scratchone.util.JedisUtil;
import me.scratchone.util.SerializeUtil;
import me.scratchone.util.UserCensorUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String token = req.getHeader("so_token");
        String token = null;
        for (Cookie cookie : req.getCookies()) {
            if(cookie.getName().equals("so_token")) {
                token = cookie.getValue();
            }
        }

        Jedis jedis = JedisUtil.getJedis();
        Long ret = jedis.del(token.getBytes());

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFlag(true);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
