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
    <title>课程管理</title>
</head>
<body>

<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/lesson/manageLesson.jsp"/>">课程管理</a></li>
                <li class="active">详细信息</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <label >课程号</label>
                            <p class="form-control-static">${lesson.lnum}</p>
                        </div>
                        <div class="form-group">
                            <label>课程名</label>
                            <p class="form-control-static">${lesson.subject}</p>
                        </div>
                        <div class="form-group">
                            <label>课程人数</label>
                            <p class="form-control-static">${lesson.ltotalnum}</p>
                        </div>
                        <div class="form-group">
                            <label>课程类型</label>
                            <p class="form-control-static">${lesson.type}</p>
                        </div>
                        <div class="form-group">
                            <label>授课老师-教师号</label>
                            <p class="form-control-static">${lesson.teacher.tnum}</p>
                        </div>
                        <div class="form-group">
                            <label>授课老师-姓名</label>
                            <p class="form-control-static">${lesson.teacher.tname}</p>
                        </div>
                        <div class="form-group">
                            <label>上课班级</label>
                            <p class="form-control-static">
                                <c:forEach items="${lesson.grades}" var="grade">
                                    ${grade.gname};
                                </c:forEach>
                            </p>
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
       window.location.href="<c:url value="/lesson/list"/>"
    });

</script>
</body>
</html>
