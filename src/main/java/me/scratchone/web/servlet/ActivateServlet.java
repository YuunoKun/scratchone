package me.scratchone.web.servlet;

import me.scratchone.service.UserService;
import me.scratchone.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activate")
public class ActivateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("acode");

        if(code == null) {
            //redirect to 404 or resend
            //yet to be implemented
        } else {
            UserService userService = new UserServiceImpl();
            userService.activate(code);
            resp.sendRedirect("http://localhost/scratchone/home.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
