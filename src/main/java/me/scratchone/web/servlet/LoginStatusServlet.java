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
import java.lang.reflect.InvocationTargetException;

@WebServlet("/loginStatusServlet")
public class LoginStatusServlet extends HttpServlet {
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

        ResultInfo resultInfo = new ResultInfo();
        if(token == null || token.length() == 0 || !jedis.exists(token.getBytes())) {
            resultInfo.setFlag(false);
        } else {
            resultInfo.setFlag(true);

            try {
                byte[] u = jedis.get(token.getBytes());
                User user = (User)SerializeUtil.deserialize(u);

                user = UserCensorUtil.censor(user);
                resultInfo.setData(user);
            } catch (Exception e) {
                e.printStackTrace();
                //yet to be logged with logger
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
