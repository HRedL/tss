<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/tools/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/tools/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/tools/css/login.css">
	<style>
	
	</style>
      <title>EAS-登录页面</title>
  </head>
  <body>
  <div id="login-background" style="position: absolute;width: 100%;height:100%;z-index: -1">
      <img src="${pageContext.request.contextPath}/views/login/login.jpg" height="100%" width="100%">
  </div>
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="background: #4682B4;">
      <div class="container">
          <div class="navbar-header">
          <div><p class="navbar-brand"  style="font-size:32px;color:#F0FFFF">EAS考试排考系统</p></div>
        </div>
      </div>
    </nav>

    <div class="container">
        <form id="form-login" class="form-signin" role="form">
            <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
            <div class="form-group has-primary has-feedback">
                <input type="text" class="form-control"
                       id="inputSuccess" name="logname"
                       placeholder="请输入登录账号" autofocus>
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
                <p><span id="logname-hint" class="msg-error"
                         style="color:#ff0000"></span></p>
            </div>
            <div class="form-group has-primary has-feedback">
                <input type="password" class="form-control"
                       id="inputSuccess4" name="password"
                       placeholder="请输入登录密码" style="margin-top:10px;">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                <p><span id="password-hint" class="msg-error"
                         style="color:#ff0000"></span></p>
            </div>
            <div class="form-group has-primary has-feedback">
                <select id="user-type" class="form-control" name="type">
                    <option value="teacher">教师</option>
                    <option value="student">学生</option>
                    <option value="manager">监考管理员</option>
                    <option value="sysmanager">系统管理员</option>
                </select>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="ck_rmbUser" value="remember-me"> 记住我
                </label>
            </div>
            <a class="btn btn-lg btn-primary btn-block" onclick="dologin()" > 登录</a>
        </form>
    </div>
    <script src="${pageContext.request.contextPath}/tools/jquery/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/tools/jquery/jquery.cookie.js"></script>
    <script src="${pageContext.request.contextPath}/tools/bootstrap/js/bootstrap.min.js"></script>
    <script>
        //登录信息及提示
    function ajaxRequest(){
        $.ajax({
            "url" : "/eas/user/handle_login",
            "data" : $("#form-login").serialize(),
            "type" : "POST",
            "dataType" : "json",
            "success" : function(obj){
                if(obj.state == 1){
                    // 保存cookie
                    saveCookie();
                    window.location.href = "/eas/user/main_page";
                } else if(obj.state==0){
                    $("#logname-hint").html(obj.message);
                }else if(obj.state=-1){
                    $("#password-hint").html(obj.message);
                }
            }
        });
    }
    //执行登录
    function dologin() {
        ajaxRequest();
    }
    </script>
    <script type="text/javascript">
        // 页面加载完成后
        // 判断在cookie中是否有"自动登录"相关信息
        // 如果有，则勾上"自动登录"，并在用户名和密码的输入框中设置默认值
        $(document).ready(function () {
            if ($.cookie("rmbUser") == "true") {
                $("#ck_rmbUser").attr("checked", true);
                $("#inputSuccess").val($.cookie("username"));
                $("#inputSuccess4").val($.cookie("password"));
                $("#user-type").val($.cookie("user_type"));
            }
        });

        // 记住用户名密码
        function saveCookie() {
            // 判断是否勾选了"自动登录"
            var checked = $("#ck_rmbUser").prop("checked");
            if (checked) {
                // 勾选了"自动登录"
                // 获取输入框中的内容
                var str_username = $("#inputSuccess").val();
                var str_password = $("#inputSuccess4").val();
                var user_type = $("#user-type").val();
                // 在cookie中记录"自动登录"，"用户名"，"密码"
                $.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
                $.cookie("username", str_username, { expires: 7 });
                $.cookie("password", str_password, { expires: 7 });
                $.cookie("user_type", user_type, { expires: 7 });
            } else {
                // 没有勾选"自动登录"
                // 清除cookie中的数据，并设置为过期
                $.cookie("rmbUser", "false", { expire: -1 });
                $.cookie("username", "", { expires: -1 });
                $.cookie("password", "", { expires: -1 });
                $.cookie("user_type", "teacher", { expires: -1 });
            }
        };
    </script>
  </body>
</html>