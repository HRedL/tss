<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/12/28
  Time: 23:44
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

    <%
        String path=request.getContextPath();
        request.setAttribute("APP_PATH",path);
    %>

    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <link rel="stylesheet" href="${APP_PATH}/tools/select2/dist/css/select2.min.css">
    <title>班级管理</title>
</head>
<body>

<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/grade/manageGrade.jsp"/>">班级管理</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form" id="update_form" method="post" action="<c:url value="/grade/update"/>">
                        <input type="hidden" id="id" value="${param.id}"/>
                        <div class="form-group">
                            <label >班级名</label>
                            <input type="text" class="form-control"  placeholder="请输入班级名" name="grade_gname" />
                        </div>
                        <div class="form-group">
                            <label>学院</label>
                            <select class="form-control" id="grade_academy">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>系</label>
                            <select class="form-control" id="grade_dept">
                            </select>
                        </div>

                        <div class="form-group">
                            <label>班级总人数</label>
                            <input type="text" class="form-control" placeholder="请输入班级总人数" name="grade_gtotalnum">
                        </div>

                        <button type="button" class="btn btn-success" id="update_btn"><i class="glyphicon glyphicon-ok"></i>提交</button>

                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="${APP_PATH}/tools/layer/layer.js"></script>
<script src="${APP_PATH}/tools/select2/dist/js/select2.min.js"></script>
<script >

    $(function () {
        initUpdateGrade();
    });

    $("#grade_academy").change(function () {
        var academy = $("#grade_academy").val();
        deptBind(academy,-1);
    });

    function initUpdateGrade() {
        var id= $("#id").val();
        $.ajax({
            url:"${APP_PATH}/grade/initUpdateGrade",
            type:"get",
            data:{
                id:id
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var gradeData=result.body.grade;

                //给页面上的内容设置默认值
                $("input[name=grade_gname]").val(gradeData.gname);
                $("input[name=grade_gtotalnum]").val(gradeData.gtotalnum);
                academyBind(gradeData.academy);
                deptBind(gradeData.academy,gradeData.dept);
            }
        });
    }

    function academyBind(academy) {
        var academy_selector=$("#grade_academy");
        academy_selector.html("");
        $.ajax({
            url:"${APP_PATH}/dict/getDictsByType",
            data:{
                "type": "academy"
            },
            type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var select_academy_body =new Array();
                var dicts=result.body.dicts;
                select_academy_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(dicts,function (i,dict) {
                    select_academy_body[i+1]={
                        "id":dict.value,
                        "text":dict.label
                    };
                });
                academy_selector.select2({
                    data:select_academy_body
                });
                academy_selector.val(academy).select2();
            }
        });
    }

    function deptBind(academy,dept) {
        var dept_selector=$("#grade_dept");
        dept_selector.html("");
        if(academy==-1){
            dept_selector.select2({
                data:[
                    {"id":-1,"text":"请选择"}
                ]
            });
            return;
        }
        $.ajax({
            url:"${APP_PATH}/dict/getDictsByTypeAndParentId",
            data:{
                "type": "dept",
                "ptype":"academy",
                "pid":academy
            },
            type:"post",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var select_dept_body=new Array();
                var dicts=result.body.dicts;
                select_dept_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(dicts,function (i,dict) {
                    select_dept_body[i+1]={
                        "id":dict.value,
                        "text":dict.label
                    };
                });
                dept_selector.select2({
                    data:select_dept_body
                });
                dept_selector.val(dept).select2();
            }
        });
    }


    $("#update_btn").click(function () {
        //获取对应的数据的值
        var id=$("#id").val();
        var gname= $("input[name=grade_gname]").val();
        var gtotalnum= $("input[name=grade_gtotalnum]").val();
        var academy= $("#grade_academy").val();
        var dept= $("#grade_dept").val();

        var jsonData={
            "id":id,
            "gname":gname,
            "academy":academy,
            "dept":dept,
            "gtotalnum":gtotalnum
        };

        updateGrade(jsonData);
    });


    function updateGrade(jsonData) {
        $.ajax({
            url:"${APP_PATH}/grade/update",
            type:"post",
            data:jsonData,
            success:function(result) {
                if(result.success==false){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                }else{
                    window.location.href="${APP_PATH}/views/manager/grade/manageGrade.jsp";
                }

            }
        });
    }





</script>
</body>
</html>
