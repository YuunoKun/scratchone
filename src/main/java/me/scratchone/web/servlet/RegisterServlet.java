package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.RegistrationState;
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
import java.util.Map;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch(Exception e) {
            e.printStackTrace();
        }

        String school = req.getParameter("school");
        SchoolService schoolService = new SchoolServiceImpl();
        School school_obj = schoolService.queryForSchool(school);
        user.setSid(school_obj.getSid());


        UserService userService = new UserServiceImpl();
        RegistrationState state = userService.register(user);

        String verify = req.getParameter("verify");
        String verificationCode = (String)req.getSession().getAttribute("VerificationCode");
        req.getSession().removeAttribute("VerificationCode");

        if(verify.equals("null") || verificationCode.equals("null") || !verify.equalsIgnoreCase(verificationCode)) {
            state = RegistrationState.VERIFICATION_CODE_ERROR;
        }

        ResultInfo resultInfo = new ResultInfo();
        switch(state) {
            case SUCCESS:
                resultInfo.setFlag(true);
                break;
            case VERIFICATION_CODE_ERROR:
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("Wrong verification code.");
                break;
            case USERNAME_TAKEN:
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("Username taken.");
                break;
            case EMAIL_TAKEN:
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("Email already registered.");
                break;
            case SERVER_ERROR:
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("Server error.");
                break;
            default: break;
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
