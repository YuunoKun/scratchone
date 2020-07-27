package me.scratchone.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/avatarQueryServlet")
public class AvatarQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uid = req.getParameter("avatarUid");
        String hasAvatar = req.getParameter("hasAvatar");

        String path = File.separator + "WEB-INF"+ File.separator + "users" + File.separator + uid + File.separator + "avatar";
        String fileName = this.getServletContext().getRealPath(path);

        File file = null;
        if(new File(fileName + ".png").exists()) {
            file = new File(fileName + ".png");
        } else if(new File(fileName + ".jpg").exists()) {
            file = new File(fileName + ".jpg");
        } else if(new File(fileName + ".jpeg").exists()) {
            file = new File(fileName + ".jpeg");
        }

        if(hasAvatar == null && file != null) {
            FileInputStream fis = new FileInputStream(file);
            OutputStream out = resp.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) != -1) {
                out.write(b, 0, len);
            }
        } else {
            if(file != null) {
                resp.getWriter().write("/avatarQueryServlet?avatarUid=" + uid);
            } else {
                resp.getWriter().write("img/avatar_2x.png");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
