<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/11/28
  Time: 19:51
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
    <%
        String APP_PATH=request.getContextPath();
        request.setAttribute("APP_PATH",APP_PATH);
    %>

    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <link rel="stylesheet" href="${APP_PATH}/tools/select2/dist/css/select2.css">
    <title>教师管理</title>
</head>
<body>

<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/teacher/manageTeacher.jsp"/>">教师管理</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form" id="update_teacher_form" method="post" action="<c:url value="/teacher/update"/>">
                        <input type="hidden" id="id" value="${param.id}"/>
                        <div class="form-group">
                            <label >教师号</label>
                            <input type="text" class="form-control"  placeholder="请输入教师号" name="tnum" />
                        </div>
                        <div class="form-group">
                            <label>教师姓名</label>
                            <input type="text" class="form-control" placeholder="请输入教师姓名" name="tname">
                        </div>
                        <div class="form-group">
                            <label>教师性别</label>
                            <select class="form-control" id="sex">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>学院</label>
                            <select class="form-control" id="academy">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>系</label>
                            <select class="form-control" id="dept">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>已监考场次</label>
                            <input type="text" class="form-control" placeholder="请输入已监考场次" name="TINVTIMES"/>
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" class="form-control" placeholder="请输入联系方式" name="ttel" />
                        </div>
                        <button type="button" class="btn btn-success" id="save_btn"><i class="glyphicon glyphicon-ok"></i>提交</button>
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
<script >

    $(function () {
        initUpdateTeacher();
    });

    $("#academy").change(function () {
        var academy = $("#academy").val();
        deptBind(academy,-1);
    });

    function initUpdateTeacher() {
        var id= $("#id").val();
        $.ajax({
            url:"${APP_PATH}/teacher/initUpdateTeacher",
            type:"get",
            data:{
                id:id
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var teacherData=result.body.teacher;

                $("input[name=tnum]").val(teacherData.tnum);
                $("input[name=tname]").val(teacherData.tname);
                $("input[name=TINVTIMES]").val(teacherData.tinvtimes);
                $("input[name=ttel]").val(teacherData.ttel);
                sexBind(teacherData.sex);
                academyBind(teacherData.academy);
                deptBind(teacherData.academy,teacherData.dept);
            }
        });
    }


    //给lesson_type下拉列表添加值
    function sexBind(sex) {
        $.ajax({
            url:"${APP_PATH}/dict/getDictsByType",
            type:"get",
            data:{
                "type":"SEX"
            },
            success:function(result) {
                var sex_data =new Array();
                var dicts=result.body.dicts;
                $.each(dicts,function (i,dict) {
                    sex_data[i]={
                        id:dict.value,
                        text:dict.label
                    };
                });
                var sex_selector=$("#sex");
                sex_selector.select2({
                    data:sex_data
                });
                sex_selector.val(sex).select2();
            }
        });
    }

    function academyBind(academy) {
        var academy_selector=$("#academy");
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
        var dept_selector=$("#dept");
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


    $("#save_btn").click(function () {

        var id=$("#id").val();
        var tnum= $("input[name=tnum]").val();
        var tname= $("input[name=tname]").val();
        var sex= $("#sex").val();
        var academy= $("#academy").val();
        var dept= $("#dept").val();
        var TINVTIMES= $("input[name=TINVTIMES]").val();
        var ttel= $("input[name=ttel]").val();
        var jsonData={
            "id":id,
            "tnum":tnum,
            "tname":tname,
            "sex":sex,
            "academy":academy,
            "dept":dept,
            "TINVTIMES":TINVTIMES,
            "ttel":ttel
        };
        updateTeacher(jsonData);
    });
    function updateTeacher(jsonData) {
        $.ajax({
            url:"${APP_PATH}/teacher/update",
            type:"post",
            data:jsonData,
            success:function(result) {
                if(result.success==false){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                    return;
                }
                window.location.href="${APP_PATH}/views/manager/teacher/manageTeacher.jsp";
            }
        });
    }

</script>
</body>
</html>
