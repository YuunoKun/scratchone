function load_navbar_content() {
    $("#navbar_schools")
        .append("<a href='/nus.html' class='dropdown-item'>National University of Singapore</a>")
        .append("<a href='#' class='dropdown-item'>Nanyang Technological University</a>")
        .append("<a href='#' class='dropdown-item'>Singapore Management University</a>")
        .append("<a href='#' class='dropdown-item'>Singapore University of Technology and Design</a>")
        .append("<a href='#' class='dropdown-item'>Singapore Institute of Technology</a>")
        .append("<a href='#' class='dropdown-item'>Singapore University of Social Sciences</a>");

    $("#navbar_majors")
        .append("<a href='#' class='dropdown-item'>Arts & Social Sciences</a>")
        .append("<a href='#' class='dropdown-item'>Accounting</a>")
        .append("<a href='#' class='dropdown-item'>Business</a>")
        .append("<a href='/computing.html' class='dropdown-item'>Computing</a>")
        .append("<a href='#' class='dropdown-item'>Design</a>")
        .append("<a href='#' class='dropdown-item'>Engineering</a>")
        .append("<a href='#' class='dropdown-item'>Law</a>")
        .append("<a href='#' class='dropdown-item'>Medicine</a>")
        .append("<a href='#' class='dropdown-item'>Performing Arts</a>")
        .append("<a href='#' class='dropdown-item'>Science</a>");

    $("#navbar_overseas_opportunities")
        .append("<a href='#' class='dropdown-item'>Australia</a>")
        .append("<a href='/uk.html' class='dropdown-item'>UK</a>")
        .append("<a href='#' class='dropdown-item'>US</a>")
        .append("<a href='#' class='dropdown-item'>China</a>")
        .append("<a href='#' class='dropdown-item'>Other European countries</a>");

    $("#navbar_forum").attr("href","/forum.html");

    $("#navbar_other_resources").attr("href","/other_re.html");
}

function load_login_status(complete) {
    ajaxRequestJSON("/loginStatusServlet","POST",null,function (data) {
        if(data.flag) {
            load_navbar_content_user();
            $("#navbar_user").html(data.data.name);
            $("#uid_cache").val(data.data.uid);
        } else {
            load_navbar_content_lns();
        }
        if(typeof(complete) === 'function') {
            complete();
        }
    })
}

function load_navbar_content_lns() {
    $("#navbarCollapse").append(
        "<ul class='navbar-nav ml-auto' id='navbar_lns'>" +
        "   <li class='nav-item' id='login'>" +
        "       <a class='nav-item nav-link' href='/login.html' style='color: white'>Login</a>" +
        "   </li>" +
        "   <li class='nav-item' id='signup'>" +
        "       <a class='nav-item nav-link' href='/signup.html' style='color: white'>Sign up</a>" +
        "   </li>" +
        "</ul>");
}

function load_navbar_content_user() {
    $("#navbar_lns").remove();
    $("#navbarCollapse").append(
        "<ul class='navbar-nav ml-auto'>" +
        "   <li class='nav-item'>" +
        "       <div class='nav-item dropdown'>" +
        "           <a href='#' class='nav-link dropdown-toggle' data-toggle='dropdown' style='color: white' id='navbar_user'>User</a>" +
        "           <div class='dropdown-menu dropdown-menu-right'>" +
        "               <a href='/user-interface.html' class='dropdown-item'>User profile</a>" +
        "               <a href='javascript:user_logout();' class='dropdown-item'>Logout</a>" +
        "           </div>" +
        "           <input type='text' id='uid_cache' disabled hidden>" +
        "       </div>" +
        "   </li>" +
        "</ul>"
    );
}

function user_logout() {
    ajaxRequestJSON("/logoutServlet","POST",null,function (data) {
        if(data.flag) {
            localStorage.removeItem("so_token");
            location.href = "/home.html";
        } else {
            alert(data.errorMsg);
        }
    })
}