package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;
import me.scratchone.domain.User;
import me.scratchone.service.UserService;
import me.scratchone.service.impl.UserServiceImpl;
import me.scratchone.util.JedisUtil;
import me.scratchone.util.SerializeUtil;
import me.scratchone.util.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService service = new UserServiceImpl();
        User u  = service.login(user);

        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();

        String verify = req.getParameter("verify");
        String verificationCode = (String)req.getSession().getAttribute("VerificationCode");
        req.getSession().removeAttribute("VerificationCode");

        if(verificationCode == null) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Please refresh this page.");
        } else if(verify.equals("null") || verificationCode.equals("null") || !verify.equalsIgnoreCase(verificationCode)) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Wrong verification code");
        } else if(u == null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Wrong username or password.");
        } else if(!"Y".equals(u.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Please verify your email.");
        } else {
            req.getSession().setAttribute("user", u);
            resultInfo.setFlag(true);


            Jedis jedis = JedisUtil.getJedis();
            String token = UuidUtil.getUuidNoReplacement();
            jedis.set(token.getBytes(), SerializeUtil.serialize(u));
            jedis.expire(token.getBytes(), 24 * 60 * 60);

//            resp.setHeader("so_token", uuidkey);
            resultInfo.setData(token);

            Cookie cookie = new Cookie("so_token", token);
            cookie.setMaxAge(60 * 60 * 12);
            resp.addCookie(cookie);
        }

        String json = mapper.writeValueAsString(resultInfo);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
