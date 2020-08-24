function load_post_status() {
    ajaxRequestJSON("/postProfileQueryServlet","POST",{"post_uid":$("#post_uid_cache").html()},function(data){
        if(data.flag) {
            $.post("/avatarQueryServlet", {"hasAvatar": "false", "avatarUid": data.data.uid}, function (data) {
                $(".post_avatar").attr("src", data);
            })
            $(".post_username").html(data.data.name);
        }
    })

    ajaxRequestJSON("/loginStatusServlet","POST",$(this).serialize(),function(data){
        if(data.flag) {
            if($("#post_uid_cache").html() == data.data.uid) {
                $("#delPost_btn").removeAttr("hidden").removeAttr("disabled");
            } else {
                $("#delPost_btn").attr("hidden","hidden").attr("disabled","disabled");
            }
        }
    })

    ajaxRequestJSON("/getPostReplyServlet","POST",$(this).serialize(),function(data){
        if(data.flag) {
            let list = data.data;
            for(let reply of Object.keys(list)) {

                let d_h = "disabled hidden";

                if(list[reply].uid == $("#uid_cache").val()) {
                    d_h = ' ';
                }

                $(".reply-list").append(
                    "<div class='reply_block'>" +
                    "   <div class='row'>" +
                    "       <div class='col-sm-2'>" +
                    "           <div class='profile-holder'>" +
                    "               <img class='post_avatar' id=\'reply_avatar_" + list[reply].rid + "\' src='/img/avatar_2x.png'/>" +
                    "           </div>" +
                    "           <div class='post_username' id=\'reply_username_" + list[reply].rid + "\'> - </div>" +
                    "       </div>" +
                    "       <div class='col-sm-10'>" +
                    "           <div class='comment col-sm-9'>" +
                    list[reply].reply +
                    "           </div>" +
                    "           <div class='postTime col-sm-3'>" +
                    list[reply].parseReleaseDate +
                    "               <span>&nbsp;</span> " +
                    "               <button id=\'reply_del_btn_" + list[reply].rid + "\' onclick=\'deleteReply(" + list[reply].rid + ", this)\' " + d_h + ">Delete</button> " +
                    "           </div>" +
                    "       </div>" +
                    "   </div>" +
                    "</div>")

                ajaxRequestJSON("/postProfileQueryServlet","POST",{"post_uid":list[reply].uid},function(data){
                    if(data.flag) {
                        $.post("/avatarQueryServlet", {"hasAvatar": "false", "avatarUid": list[reply].uid}, function (data) {
                            $("#reply_avatar_" + list[reply].rid).attr("src", data);
                        })
                        $("#reply_username_" + list[reply].rid).html(data.data.name);
                    }
                })
            }
        }
    })
}

function deleteReply(rid, obj) {
    ajaxRequestJSON("/deletePostReplyServlet","POST",{"reply_rid":rid},function(data){
        if(data.flag) {
            obj.parentNode.parentNode.parentNode.parentNode.remove();
        } else {
            alert(data.errorMsg);
        }
    })
}

function deletePost() {
    ajaxRequestJSON("/deletePostServlet","POST",$(this).serialize(),function(data){
        if(data.flag) {
            location.href = "/forum.html";
        } else {
            alert(data.errorMsg);
        }
    })
}

function submitNewReply() {
    ajaxRequestJSON("/loginStatusServlet","POST",$(this).serialize(),function(data){
        let user = data.data;
        if(data.flag) {
            if($(".type-comment").val() !== '') {
                ajaxRequestJSON("/addPostReplyServlet","POST",{
                    "reply_uid":$("#uid_cache").val(),
                    "reply_reply":$(".type-comment").val()
                },function(data){
                    if(data.flag) {
                        $(".reply-list").append(
                            "<div class='reply_block'>" +
                            "   <div class='row'>" +
                            "       <div class='col-sm-2'>" +
                            "           <div class='profile-holder'>" +
                            "               <img class='post_avatar' id='new_reply_avatar' src='/img/avatar_2x.png'/>" +
                            "           </div>" +
                            "           <div class='post_username'>" + user.name + "</div>" +
                            "       </div>" +
                            "       <div class='col-sm-10'>" +
                            "           <div class='comment'>" +
                            $(".type-comment").val() +
                            "           </div>" +
                            "           <div class='postTime'>" +
                            data.data +
                            "               <span>&nbsp;</span> " +
                            "               <button onclick=\'deleteReply(" + data.errorMsg + ",this)\'>Delete</button> " +
                            "           </div>" +
                            "       </div>" +
                            "   </div>" +
                            "</div>");

                        $(".type-comment").val('');
                        $.post("/avatarQueryServlet", {"hasAvatar": "false", "avatarUid": user.uid}, function (data) {
                            $("#new_reply_avatar").attr("src", data);
                        })
                    } else {
                        alert(data.errorMsg);
                    }
                })
            } else {
                alert("You cannot submit empty comments!");
            }
        } else {
            alert("You need to login to post comments!");
        }
    })
}