<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/11/28
  Time: 19:45
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
    <title>EAS-新增用户信息</title>
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
                <li class="active">添加</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <%--method="post" action="<c:url value="/user/add"/>"--%>
                    <form role="form" id="add_user_form" >
                        <div class="form-group">
                            <label>用户账号</label>
                            <input type="text" class="form-control" placeholder="请输入用户账号" name="addlogname">
                        </div>
                        <div class="form-group">
                            <label>登录密码</label>
                            <input type="text" class="form-control" placeholder="请输入登录密码" name="password">
                        </div>
                        <div class="form-group">
                            <label>用户名</label>
                            <input type="text" class="form-control" placeholder="请输入用户名" name="addname" >
                        </div>
                        <div class="form-group">
                            <label>用户头像</label>
                            <input type="text" class="form-control"   disabled="disabled" >
                            <input type="hidden" name="pic" id="pic">
                        </div>
                        <div class="form-group">
                            <label>用户头像是否自定义</label>
                            <select class="form-control" name="exist_pic" id="exist_pic">
                                <option value="1">是</option>
                                <option value="2">否</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>类型</label>
                            <select class="form-control" name="type" id="type">
                                <option value="2">学生</option>
                                <option value="3">教师</option>
                                <option value="4">监考管理员</option>
                            </select>
                        </div>
                        <button type="button" class="btn btn-success" id="save_btn"><i class="glyphicon glyphicon-ok"></i> 新增</button>
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
<script src="${PATH}/tools/layer/layer.js"></script>
<script >
    $("#save_btn").click(function () {
        var logname=$("input[name=addlogname]").val();
        var password=$("input[name=password]").val();
        var name=$("input[name=addname]").val();
        var pic=$("input[name=pic]").val();
        var exist_pic=$("#exist_pic").val();
        var type= $("#type").val();
        var jsonData={
            "logname":logname,
            "password":password,
            "name":name,
            "pic":pic,
            "exist_pic":exist_pic,
            "type":type,
        }
        addUser(jsonData);
    })
    function addUser(jsonData) {
        $.ajax({
            url:"${PATH}/user/add",
            type:"post",
            data:jsonData,
            success:function(result) {
                if(result.success==false){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                }else{
                    layer.msg(result.msg,{time:3000,icon:6,shift:6});
                    window.location.href="${PATH}/views/sysmanager/user/manageUser.jsp";
                }
            }
        });
    }


    $("#reset_btn").click(function () {
        $('#add_user_form')[0].reset();
    })




</script>
</body>
</html>


