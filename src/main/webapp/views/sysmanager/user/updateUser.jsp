<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/11/28
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="GB18030">
<head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <title>EAS-修改用户信息</title>
</head>
<body>
<%
    String PATH=request.getContextPath();
    request.setAttribute("PATH",PATH);
%>
<jsp:include page="../common/sysManagerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/sysManagerMenu.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/user/list"/>">用户管理</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form" id="update_user_form" method="post" action="<c:url value="/user/update"/>">
                        <div class="form-group">
                            <label >用户账号</label>
                                <input type="hidden" name="logname" value="${user.logname}">
                            <input type="text" class="form-control"   value="${user.logname}" disabled="disabled">
                        </div>
                        <div class="form-group">
                            <label>登录密码</label>
                            <input type="text" class="form-control" placeholder="请输入密码" name="password" value="${user.password}">
                        </div>
                        <div class="form-group">
                            <label>用户名</label>
                            <input type="text" class="form-control" placeholder="请输入用户名" name="name" value="${user.name}">
                        </div>
                        <div class="form-group">
                            <label>用户头像</label>
                            <input type="hidden" name="pic" value="${user.pic}">
                            <input type="text" class="form-control"   value="${user.pic}" disabled="disabled">
                        </div>
                        <div class="form-group">
                            <label>类型</label>
                            <select class="form-control" name="type">
                                <option value="2">学生</option>
                                <option value="3">老师</option>
                                <option value="4">监考管理员</option>
                            </select>
                        </div>
                        <button type="button" class="btn btn-success" id="save_btn"><i class="glyphicon glyphicon-ok"></i>提交</button>
                        <button type="button" class="btn btn-danger" id="reset_btn"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>
<script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/tools/script/docs.min.js"/>"></script>
<script >

    $(function () {
        var exist_pic= 1;
        if(exist_pic==1){
            $("select option[value='1']").attr("selected",true);
        }else if(exist_pic==2){
            $("select option[value='2']").attr("selected",true);
        }
    });

    $(function () {
        var type= ${user.type};
        if(type==1){
            $("select option[value='1']").attr("selected",true);
        }else if(type==2){
            $("select option[value='2']").attr("selected",true);
        }
        else if(type==3){
            $("select option[value='3']").attr("selected",true);
        }
    });

    $("#save_btn").click(function () {
        $("#update_user_form").submit();
    });

    $("#reset_btn").click(function () {
        $("#update_user_form")[0].reset();
    })

</script>
</body>
</html>
