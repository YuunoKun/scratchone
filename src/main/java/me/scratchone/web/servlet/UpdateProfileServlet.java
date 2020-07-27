package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;
import me.scratchone.domain.School;
import me.scratchone.domain.User;
import me.scratchone.service.SchoolService;
import me.scratchone.service.UserService;
import me.scratchone.service.impl.SchoolServiceImpl;
import me.scratchone.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/updateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        ResultInfo resultInfo = new ResultInfo();
        if(user.getUid() != 0) {
            String schoolName = map.get("school")[0];
            SchoolService schoolService = new SchoolServiceImpl();
            School school = schoolService.queryForSchool(schoolName);
            user.setSid(school.getSid());


            try {
                UserService userService = new UserServiceImpl();
                userService.updateProfile(user);
                resultInfo.setFlag(true);
            } catch(Exception e) {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("Server error. Failed to update.");
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Server error.");
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
