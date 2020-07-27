package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;
import me.scratchone.domain.School;
import me.scratchone.service.SchoolService;
import me.scratchone.service.impl.SchoolServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/schoolQueryServlet")
public class SchoolQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SchoolService schoolService = new SchoolServiceImpl();
        List<School> schools = null;
        if(req.getParameter("queryAllSchools") != null) {
            schools = schoolService.queryForAllSchools();
        } else {
            String school_type = req.getParameter("school_type");
            schools = schoolService.queryForSchools(school_type);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(schools);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}