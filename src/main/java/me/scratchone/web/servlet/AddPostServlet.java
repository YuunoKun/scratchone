package me.scratchone.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.scratchone.domain.ForumPost;
import me.scratchone.domain.ResultInfo;
import me.scratchone.service.ForumService;
import me.scratchone.service.impl.ForumServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/addPostServlet")
public class AddPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultInfo resultInfo = new ResultInfo();

        String post_uid = req.getParameter("post_uid");
        String post_releaseDate = req.getParameter("post_releaseDate");
        String post_title = req.getParameter("post_title");
        String post_content = req.getParameter("post_content");

        ForumPost forumPost = new ForumPost();

        ForumService forumService = new ForumServiceImpl();
        forumPost.setPid(forumService.getForumPostCount() + 1);
        forumPost.setUid(Integer.parseInt(post_uid));
        forumPost.setTitle(post_title);

        try {
            Date date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zz").parse(post_releaseDate);
            forumPost.setReleaseDate(date);
            forumPost.setParseReleaseDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date));

            String fileName = "fp-" + forumPost.getPid() + ".html";
            String savingFolder = this.getServletContext().getRealPath(File.separator + "forum");
            String fileSavingPath = savingFolder + File.separator + fileName;

            File f = new File(savingFolder);
            if(!f.exists()){
                f.mkdirs();
            }

            StringBuilder sb = new StringBuilder();

            sb.append("<!DOCTYPE html>\n");
            sb.append("<html lang='en'>\n");
            sb.append("<head>\n");
            sb.append("    <meta charset='UTF-8'>\n");
            sb.append("    <meta name='viewport' content='width=device-width, initial-scale=1'>\n");
            sb.append("    <link href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' rel='stylesheet'/>\n");
            sb.append("    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>\n");
            sb.append("    <script src='../js/jquery-3.5.1.min.js'></script>\n");
            sb.append("    <script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js'></script>\n");
            sb.append("    <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js'></script>\n");
            sb.append("    <script src='../js/navbar_content.js'></script>\n");
            sb.append("    <script src='../js/so_common.js'></script>\n");
            sb.append("    <script src='../js/so_fptemp.js'></script>\n");
            sb.append("    <link href='fp.css' rel='stylesheet'>\n");
            sb.append("    <title>ScratchOne Forum - ").append(forumPost.getTitle()).append("</title>\n");


            sb.append("    <script>\n");
            sb.append("        $(function () {\n");
            sb.append("            load_navbar_content();\n");
            sb.append("            load_login_status(() => load_post_status());\n");
            sb.append("        })\n");
            sb.append("    </script>\n");

            sb.append("</head>\n");

            sb.append("<body>\n");

            sb.append("<div class='container-fluid'>\n");
            sb.append("    <section class='flex'>\n");
            sb.append("        <div class='content'>\n");
            sb.append("            <div class='top'>\n");
            sb.append("                <img src='../img/logo.png' alt='logo' id='logo'/>\n");
            sb.append("            </div>\n");

            //navbar
            sb.append("            <nav class='navbar navbar-expand-lg navbar-dark ' style='clear: both; background-color:black;width: 100%'>\n");
            sb.append("                <li class='nav-item' id='now'><a href='/forum.html' class='nav-link' style='color: white'>Forum</a></li>\n");
            sb.append("                <button type='button' class='navbar-toggler' data-toggle='collapse' data-target='#navbarCollapse'>\n");
            sb.append("                    <span class='navbar-toggler-icon'></span>\n");
            sb.append("                </button>\n");
            sb.append("                <div class='collapse navbar-collapse' id='navbarCollapse'>\n");
            sb.append("                    <ul class='navbar-nav mr-auto'>\n");
            sb.append("                        <li class='nav-item'>\n");
            sb.append("                            <a class='nav-link' href='/home.html' style='color: white'>Home</a>\n");
            sb.append("                        </li>\n");
            sb.append("                        <li class='nav-item'>\n");
            sb.append("                            <div class='nav-item dropdown'>\n");
            sb.append("                                <a href='#' class='nav-link dropdown-toggle' data-toggle='dropdown'\n");
            sb.append("                                   style='color: white'>School</a>\n");
            sb.append("                                <div class='dropdown-menu' id='navbar_schools'></div>\n");
            sb.append("                            </div>\n");
            sb.append("                        </li>\n");
            sb.append("                        <li class='nav-item'>\n");
            sb.append("                            <div class='nav-item dropdown'>\n");
            sb.append("                                <a href='#' class='nav-link dropdown-toggle' data-toggle='dropdown'\n");
            sb.append("                                   style='color: white'>Majors</a>\n");
            sb.append("                                <div class='dropdown-menu' id='navbar_majors'></div>\n");
            sb.append("                            </div>\n");
            sb.append("                        </li>\n");
            sb.append("                        <li class='nav-item'>\n");
            sb.append("                            <div class='nav-item dropdown'>\n");
            sb.append("                                <a href='#' class='nav-link dropdown-toggle' data-toggle='dropdown'\n");
            sb.append("                                   style='color: white'>Overseas\n");
            sb.append("                                    Opportunities</a>\n");
            sb.append("                                <div class='dropdown-menu' id='navbar_overseas_opportunities'></div>\n");
            sb.append("                            </div>\n");
            sb.append("                        </li>\n");
            sb.append("                        <li class='nav-item'>\n");
            sb.append("                            <a class='nav-item nav-link' style='color: white' id='navbar_other_resources'>Other\n");
            sb.append("                                resources</a>\n");
            sb.append("                        </li>\n");
            sb.append("                    </ul>\n");
            sb.append("                </div>\n");
            sb.append("            </nav>\n");


            sb.append("            <div>\n");
            sb.append("                <div class='post_title'>").append(forumPost.getTitle()).append("</div>\n");
            sb.append("                <div class='post_block'>\n");
            sb.append("                    <div id='post_uid_cache' disabled hidden>" + post_uid + "</div>\n");
            sb.append("                    <div class='row'>\n");
            sb.append("                        <div class='col-sm-2'>\n");
            sb.append("                            <div class='profile-holder'>\n");
            sb.append("                                <img class='post_avatar' src='/img/avatar_2x.png'/>\n");
            sb.append("                            </div>\n");
            sb.append("                            <div class='post_username'>Admin</div>\n");
            sb.append("                        </div>\n");
            sb.append("                        <div class='col-sm-10'>\n");
            sb.append("                            <div class='post_content col-sm-9'>\n");
            sb.append("                                ").append(post_content).append("\n");
            sb.append("                            </div>\n");
            sb.append("                            <div class='postTime col-sm-3'>\n");
            sb.append("                                ").append(forumPost.getParseReleaseDate()).append("\n");
            sb.append("                                <span>&nbsp &nbsp</span>\n");
            sb.append("                                <button id='delPost_btn' onclick='deletePost()' disabled hidden>Delete post</button>\n");
            sb.append("                            </div>\n");
            sb.append("                        </div>\n");
            sb.append("                    </div>\n");
            sb.append("                </div>\n");
            sb.append("                <div class='reply-list'></div>\n");
            sb.append("                <form class='form-horizontal'>\n");
            sb.append("                    <div class='form-group'>\n");
            sb.append("                        <div class='comment-holder'>\n");
            sb.append("                            <label>Add your new comment here:</label>\n");
            sb.append("                            <textarea placeholder='Comment here' class='type-comment'></textarea>\n");
            sb.append("                            <br/>\n");
            sb.append("                            <button type='button' class='submit_comment pull-right' onclick='submitNewReply()'>\n");
            sb.append("                                Submit\n");
            sb.append("                            </button>\n");
            sb.append("                        </div>\n");
            sb.append("                    </div>\n");
            sb.append("                </form>\n");
            sb.append("            </div>\n");
            sb.append("        </div>\n");
            sb.append("        <div class='bottom'>Copyright @ 2020 Scratch One</div>\n");
            sb.append("    </section>\n");
            sb.append("</div>\n");

            sb.append("</body>\n");
            sb.append("</html>");


            PrintStream printStream = new PrintStream(new File(fileSavingPath));
            printStream.println(sb.toString());

            forumPost.setLink("/forum/" + fileName);

            forumService.createForumPost(forumPost);

            resultInfo.setFlag(true);
        } catch (ParseException e) {
            e.printStackTrace();

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("Server error. Failed to create post.");
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
