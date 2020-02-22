<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <%
        String path=request.getContextPath();
        request.setAttribute("APP_PATH",path);
    %>


    <link rel="stylesheet" href="${APP_PATH}/tools/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/tools/css/font-awesome.min.css ">
    <link rel="stylesheet" href="${APP_PATH}/tools/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/tools/select2/dist/css/select2.css">
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
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <label >课程</label>
                            <input type="text" class="form-control"  placeholder="请输入课程号" name="lnum">
                        </div>
                        <div class="form-group">
                            <label>课程名</label>
                            <input type="text" class="form-control" placeholder="请输入课程名" name="subject">
                        </div>
                        <div class="form-group">
                            <label>课程人数</label>
                            <input type="text" class="form-control" placeholder="请输入课程人数" name="ltotalnum">
                        </div>
                        <div class="form-group">
                            <label>课程类型</label>
                            <select class="form-control" id="select_lesson_type">
                            </select>
                        </div>


                        <div class="form-group">
                            <label>授课教师</label>
                            <select class="form-control" id="select_teachers">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>班级号</label>
                            <select class="form-control" id="select_grades">
                            </select>
                        </div>
                        <button type="button" class="btn btn-success" id="add_btn"><i class="glyphicon glyphicon-ok"></i> 新增</button>
                    </form>

                </div>
            </div>

        </div>
    </div>
</div>
<script src="${APP_PATH}/tools/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/tools/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/tools/layer/layer.js"></script>
<script src="${APP_PATH}/tools/select2/dist/js/select2.js"></script>
<script>

    //页面初始化，即给下拉列表添加内容
    $(function () {
        lessonTypeBind();
        teacherBind();
        gradeBind();
    });


    //给lesson下拉列表添加值
    function lessonTypeBind() {
        $.ajax({
            url:"${APP_PATH}/dict/getDictsByType",
            type:"get",
            data:{
                "type":"LTYPE"
            },
            success:function(result) {
                var select_lesson_type =new Array();
                var dicts=result.body.dicts;
                $.each(dicts,function (i,dict) {
                    select_lesson_type[i]={
                        id:dict.value,
                        text:dict.label
                    };
                });
                $("#select_lesson_type").select2({
                    data:select_lesson_type
                });
            }
        });
    }

    //给teacher下拉列表添加值
    function teacherBind() {
        $.ajax({
            url:"${APP_PATH}/teacher/getAllTeachers",
            type:"get",
            success:function(result) {
                var select_teachers =new Array();
                var teachers=result.body.teachers;
                $.each(teachers,function (i,teacher) {
                    select_teachers[i]={
                         id:teacher.id,
                         text:teacher.tname+"("+teacher.tnum+")"
                    };
                });
                $("#select_teachers").select2({
                    data:select_teachers
                });
            }
        });
    }


    //给grade下拉列表添加值
    function gradeBind() {
        $.ajax({
            url:"${APP_PATH}/grade/getAllGrades",
            type:"get",
            success:function(result) {
                var select_grades =new Array();
                var grades=result.body.grades;
                $.each(grades,function (i,grade) {
                    select_grades[i]={
                        id:grade.id,
                        text:grade.gname
                    };
                });
                $("#select_grades").select2({
                    data: select_grades,
                    multiple: true,
                    maximumSelectionLength: 8  //最多能够选择的个数
                });
            }
        });
    }


    //添加按钮点击，判断后提交
    $("#add_btn").click(function () {
        var lnum= $("input[name=lnum]").val();
        var subject= $("input[name=subject]").val();
        var ltotalnum= $("input[name=ltotalnum]").val();
        var type= $("#select_lesson_type").val();
        var tid= $("#select_teachers").val();
        var gids= $("#select_grades").val();

        if(lnum==""){
            layer.msg('请输入课程号',{time:3000,icon:3,shift:6});
            return;
        }
        if(subject==""){
            layer.msg('请输入课程名',{time:3000,icon:3,shift:6});
            return;
        }
        if(ltotalnum==""){
            layer.msg('请输入课程人数',{time:3000,icon:3,shift:6});
            return;
        }
        if(gids==null){
            layer.msg('请选择班级',{time:3000,icon:3,shift:6});
            return;
        }
        var gidsData="";
        $.each(gids,function (i,gid) {
            gidsData+=gid;
            gidsData+=",";
        });
        var jsonData={
            "lnum":lnum,
            "subject":subject,
            "ltotalnum":ltotalnum,
            "type":type,
            "tid":tid,
            "gids":gidsData
        };
        $.ajax({
            url:"${APP_PATH}/lesson/validateLesson",
            type:"post",
            data:{
              "lnum":lnum
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var success=result.success;
                var msg=result.msg;
                if(success==false){
                    layer.msg(msg,{time:3000,icon:3,shift:6});
                    return;
                }
                addLesson(jsonData);
            }
        });

        //添加lesson的按钮
        function addLesson(jsonData) {
            $.ajax({
               url:"${APP_PATH}/lesson/add",
               type:"post",
               data:jsonData,
                success:function(result) {

                   if(result.success==false){
                       layer.msg(result.msg,{time:3000,icon:3,shift:6});
                       return;
                   }
                   window.location.href="${APP_PATH}/views/manager/lesson/manageLesson.jsp";
                }
               });
        }


    });
</script>
</body>
</html>
