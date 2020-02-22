<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <input type="hidden" id="lesson_id" value="${param.id}">
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
                        <button type="button" class="btn btn-success" id="update_btn"><i class="glyphicon glyphicon-ok"></i> 提交</button>
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

    $(function () {
        initUpdateLesson();
    });

    function initUpdateLesson() {
        var lesson_id= $("#lesson_id").val();
        $.ajax({
            url:"${APP_PATH}/lesson/initUpdateLesson",
            type:"get",
            data:{
                id:lesson_id
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var lessonData=result.body.lesson;

                //给页面上的内容设置默认值
                var lnum_selector= $("input[name=lnum]");
                lnum_selector.val(lessonData.lnum);
                lnum_selector.attr("default_value",lessonData.lnum);

                $("input[name=subject]").val(lessonData.subject);
                $("input[name=ltotalnum]").val(lessonData.ltotalnum);


                lessonTypeBind(lessonData.type);
                teacherBind(lessonData.teacher.id);
                var gradeData=new Array();
                $.each(lessonData.grades,function(i,grade){
                    gradeData[i]=grade.id;
                });
                gradeBind(gradeData);
            }
        });
    }


    //给lesson_type下拉列表添加值
    function lessonTypeBind(defaultLessonType) {
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
                var lesson_type_selector=$("#select_lesson_type");
                lesson_type_selector.select2({
                    data:select_lesson_type
                });
                lesson_type_selector.val(defaultLessonType).select2();
            }
        });
    }

    //给teacher下拉列表添加值
    function teacherBind(defaultTeacher) {
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
                var teachers_selector=$("#select_teachers");
                teachers_selector.select2({
                    data:select_teachers
                });
                teachers_selector.val(defaultTeacher).select2();
            }
        });
    }


    //给grade下拉列表添加值
    function gradeBind(defaultGrade) {
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
                var grades_selector=$("#select_grades");
                grades_selector.select2({
                    data: select_grades,
                    multiple: true,
                    maximumSelectionLength: 8  //最多能够选择的个数
                });
                grades_selector.val(defaultGrade).select2();
            }
        });
    }



    $("#update_btn").click(function () {
        //获取对应的数据的值
        var lnum_selector= $("input[name=lnum]");
        var lnum=lnum_selector.val();
        var subject= $("input[name=subject]").val();
        var ltotalnum= $("input[name=ltotalnum]").val();
        var type= $("#select_lesson_type").val();
        var tid= $("#select_teachers").val();
        var gids= $("#select_grades").val();
        var lid=$("#lesson_id").val();

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
            "id":lid,
            "lnum":lnum,
            "subject":subject,
            "ltotalnum":ltotalnum,
            "type":type,
            "tid":tid,
            "gids":gidsData
        };

        //如果已经修改了课程号，需要验证当前课程号是否在数据库中有对应的数据
        var default_lnum=lnum_selector.attr("default_value");
        if(default_lnum!=lnum){
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
                    }else{
                        updateLesson(jsonData);
                    }
                }
            });
        }else{
            updateLesson(jsonData);
        }

    });
    function updateLesson(jsonData) {
        $.ajax({
            url:"${APP_PATH}/lesson/update",
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






</script>
</body>
</html>
