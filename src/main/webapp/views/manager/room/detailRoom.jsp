<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/12/29
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">


<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <title>教室管理</title>
</head>
<body>

<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/room/manageRoom.jsp"/>">教室管理</a></li>
                <li class="active">详细信息</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">

                        <div class="form-group">
                            <label >教学楼</label>
                            <p class="form-control-static">${room.id}</p>
                        </div>
                        <div class="form-group">
                            <label>教室号</label>
                            <p class="form-control-static">${room.rnum}</p>
                        </div>
                        <div class="form-group">
                            <label>容量</label>
                            <p class="form-control-static">${room.capacity}</p>
                        </div>
                        <div class="form-group">
                            <label>所需监考教师人数</label>
                            <p class="form-control-static">${room.ttotalnum}</p>
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
        window.location.href="<c:url value="/room/list"/>"
    });

</script>
</body>
</html>

