package me.scratchone.web.filter;

import me.scratchone.util.JedisUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LoginFilter implements Filter {

    private static List<String> passUrls = new ArrayList<>();

    private String ctxPath = null;

    private static String redirectUrl = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        String ignoreURL = filterConfig.getInitParameter("ignoreURL");
        redirectUrl = filterConfig.getInitParameter("redirectURL");

        String[] ignoreURLArray = ignoreURL.split(",");
        for (String url : ignoreURLArray) {
            passUrls.add(url.trim());
        }
        ctxPath = filterConfig.getServletContext().getContextPath();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        String token = request.getHeader("so_token");
        String token = null;
        for (Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals("so_token")) {
                token = cookie.getValue();
            }
        }

        Jedis jedis = JedisUtil.getJedis();

        if(token == null || token.length() == 0 || !jedis.exists(token.getBytes())) {
            response.sendRedirect(ctxPath + "/" + redirectUrl);
        } else {
            jedis.expire(token.getBytes(), 12 * 60 * 60);
            filterChain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() { }
}
