<%--
  Created by IntelliJ IDEA.
  User: 叶之于秋
  Date: 2019/5/9
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/Style/StudentStyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/Style/ks.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .rs_content {
            background: #fff;
            position: relative;
            z-index: 1;
            padding: 30px 20px 80px 20px;
            font: 16px simhei;
            border-top: 1px solid #cfe1f9;
            box-shadow: 4px 4px 12px #cfe1f9;
        }
        .change_password_title {
            font: 16px simhei;
            color: #333;
            padding-bottom: 9px;
            border-bottom: 1px solid #e8e8e8;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .new_password, .confirm_password {
            margin-bottom: 20px;
        }
        .word {
            display: inline-block;
            width: 100px;
        }
        .new_password>input, .confirm_password>input {
            width: 158px;
            height: 24px;
            border: 1px solid #b8b8b8;
        }
        .save_password {
            width: 140px;
            height: 42px;
            line-height: 42px;
            text-align: center;
            background: -moz-linear-gradient(top, #27b1f6 0%, #0aa1ed 100%);
            background: -webkit-linear-gradient(top, #27b1f6 0%, #0aa1ed 100%);
            background: -o-linear-gradient(top, #27b1f6 0%, #0aa1ed 100%);
            background: -ms-linear-gradient(top, #27b1f6 0%, #0aa1ed 100%);
            color: #fff;
            border-radius: 2px;
            cursor: pointer;
            margin-top: 30px;
        }
    </style>
    <title>EAS-个人信息界面</title>
</head>
<body>
<jsp:include page="common/teacherTitle.jsp" />

<div class="container-fluid">
    <div class="row">

        <jsp:include page="common/teacherMenu.jsp"/>

        <div class="rightbox"
             style="margin-top: 60px; margin-right:250px; float: right; width: 60%">
            <h2 class="mbx" style="font-size: 15px">我的信息 &gt; 安全管理 &nbsp;&nbsp;&nbsp;</h2>
            <div class="morebt">
                <ul id="ulStudMsgHeadTab">
                    <li><a class="tab2" onclick=""
                           href="${pageContext.request.contextPath}/teacher/show_personage">
                        个人资料
                    </a>
                    </li>
                    <li><a class="tab2" onclick=""
                           href="${pageContext.request.contextPath}/teacher/show_changePassword">
                        安全管理
                    </a>
                    </li>
                </ul>
            </div>

            <div class="rs_content">
                <form id="form-pwd">
                <p class="change_password_title">更改密码</p>
                <div class="new_password">
                    <span class="word">输入旧密码：</span>
                    <input name="old-pwd" type="password"/>
                    <span id="oldPwd-hint" class="msg-error" style="color:#ff0000"></span>
                </div>
                <div class="new_password">
                    <span class="word">输入新密码：</span>
                    <input name="new-pwd" type="password"/>
                    <span id="newPwd-hint" class="msg-error" style="color:#ff0000"></span>
                </div>
                <div class="confirm_password">
                    <span class="word">确认新密码：</span>
                    <input name="confirm-pwd" type="password"/>
                    <span id="confirmPwd-hint" class="msg-error" style="color:#ff0000"></span>
                </div>
                <div class="save_password" onclick="savePwd()">
                    保存更改
                </div>
                </form>
            </div>

        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/tools/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/tools/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/tools/script/docs.min.js"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/i18n/jquery.jBox-zh-CN.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/Common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/Data.js" type="text/javascript"></script>

<script type="text/javascript">

    function savePwd(){
        $.ajax({
            "url" : "/eas/user/update_Pwd",
            "data" : $("#form-pwd").serialize(),
            "type" : "POST",
            "dataType" : "json",
            "success" : function(obj){
                if(obj.state == 1){
                    alert(obj.message + "请重新登录！");
                    window.location.href = "/eas/user/login";
                } else if(obj.state == 0){
                    alert(obj.message);
                } else if(obj.state == -1){
                    $("#oldPwd-hint").html(obj.message);
                } else if(obj.state == -2){
                    $("#confirmPwd-hint").html(obj.message);
                }
            }
        });
    }

</script>
</body>
</html>
