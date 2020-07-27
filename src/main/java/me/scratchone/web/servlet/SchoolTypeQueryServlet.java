package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;
import me.scratchone.domain.SchoolType;
import me.scratchone.service.SchoolTypeService;
import me.scratchone.service.impl.SchoolTypeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/schoolTypeQueryServlet")
public class SchoolTypeQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SchoolTypeService schoolTypeService = new SchoolTypeServiceImpl();

        List<SchoolType> allSchoolTypes = schoolTypeService.getAllSchoolTypes();

        ResultInfo resultInfo = new ResultInfo();
        if(allSchoolTypes != null) {
            resultInfo.setFlag(true);
            resultInfo.setData(allSchoolTypes);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Failed to query all school types.");
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
