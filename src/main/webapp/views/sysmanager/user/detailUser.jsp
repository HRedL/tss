<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/11/8
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>查看用户信息</title>

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
                <li class="active">详情</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <label >用户账号</label>
                            <p class="form-control-static">"${user.logname}</p>
                        </div>
                        <div class="form-group">
                            <label>登录密码</label>
                            <p class="form-control-static">${user.password}</p>
                        </div>
                        <div class="form-group">
                            <label>用户名</label>
                            <p class="form-control-static">${user.name}</p>
                        </div>
                        <div class="form-group">
                            <label>用户头像</label>
                            <p class="form-control-static">${user.pic}</p>
                        </div>
                        <div class="form-group">
                            <label>用户头像是否自定义</label>
                            <p class="form-control-static">${user.exist_pic}</p>
                        </div>
                        <div class="form-group">
                            <label>类型</label>
                            <p class="form-control-static">${user.type}</p>
                        </div>
                        <button type="button" class="btn btn-success" id="return_btn"><i class="glyphicon glyphicon-plus"></i>返回</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/tools/script/docs.min.js"/>"></script>
<script>
    $("#return_btn").click(function(){
        window.location.href="<c:url value="/user/list"/>"
    });
</script>
</body>
</html>
