package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/uploadProfileImgServlet")
@MultipartConfig
public class UploadProfileImgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = Integer.parseInt(req.getParameter("avatarUid"));

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        Part part = req.getPart("profile_img");

        String headerInfo = part.getHeader("content-disposition");
        String fileName = "avatar" + headerInfo.substring(headerInfo.lastIndexOf("."), headerInfo.length() - 1);
        //String fileName = headerInfo.substring(headerInfo.lastIndexOf("=") + 2, headerInfo.length() - 1);
        String fileSavingFolder = this.getServletContext().getRealPath(File.separator + "WEB-INF"+ File.separator + "users" + File.separator + uid + File.separator);
        String fileSavingPath = fileSavingFolder + File.separator + fileName;

        File f = new File(fileSavingFolder + File.separator);
        if(!f.exists()){
            f.mkdirs();
        }
        part.write(fileSavingPath);

        ResultInfo resultInfo = new ResultInfo();
        File testFile = new File(fileSavingPath);
        if(testFile.exists()) {
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Failed to upload file.");
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
