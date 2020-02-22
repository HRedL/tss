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
    <title>学生管理</title>
</head>
<body>
<%
    String PATH=request.getContextPath();
    request.setAttribute("PATH",PATH);
%>
<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/student/manageStudent.jsp"/>">学生管理</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form" id="update_student_form" method="post" action="<c:url value="/student/update"/>">
                        <input type="hidden" id="id" value="${param.id}"/>
                        <div class="form-group">
                            <label >学院</label>
                            <select class="form-control" id="student_grade_academy">
                            </select>
                        </div>
                        <div class="form-group">
                            <label >系</label>
                            <select class="form-control" id="student_grade_dept">
                            </select>
                        </div>

                        <div class="form-group">
                            <label >班级名</label>
                            <select class="form-control" id="student_grade_gname">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>学号</label>
                            <input type="text" class="form-control" placeholder="请输入学号" name="student_snum" >
                        </div>
                        <div class="form-group">
                            <label>学生姓名</label>
                            <input type="text" class="form-control" placeholder="请输入学生姓名" name="student_sname">
                        </div>
                        <div class="form-group">
                            <label>学生性别</label>
                            <select class="form-control" id="student_sex">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>学生联系方式</label>
                            <input type="text" class="form-control" placeholder="请输入学生联系方式" name="student_stel">
                        </div>
                        <button type="button" class="btn btn-success" id="save_btn"><i class="glyphicon glyphicon-ok"></i>提交</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>
<script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/tools/layer/layer.js"/>"></script>
<script src="${APP_PATH}/tools/select2/dist/js/select2.js"></script>
<script >

    $(function () {
        initUpdateStudent();
    });

    $("#student_grade_academy").change(function () {
        var academy = $("#student_grade_academy").val();
        deptBind(academy,-1);
        gradeBind(academy,-1,-1);
    });

    $("#student_grade_dept").change(function () {
        var academy = $("#student_grade_academy").val();
        var dept = $("#student_grade_dept").val();
        gradeBind(academy,dept,-1);
    });

    function initUpdateStudent() {
        var id= $("#id").val();
        $.ajax({
            url:"${APP_PATH}/student/initUpdateStudent",
            type:"get",
            data:{
                id:id
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var studentData=result.body.student;

                $("input[name=student_snum]").val(studentData.snum);
                $("input[name=student_sname]").val(studentData.sname);
                $("input[name=student_stel]").val(studentData.stel);
                if(studentData.sex==null){
                    sexBind(1);
                }
                else {
                    sexBind(studentData.sex);
                }
                academyBind(studentData.grade.academy);
                deptBind(studentData.grade.academy,studentData.grade.dept);
                gradeBind(studentData.grade.academy, studentData.grade.dept, studentData.grade.id);
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
                var sex_selector=$("#student_sex");
                sex_selector.select2({
                    data:sex_data
                });
                sex_selector.val(sex).select2();
            }
        });
    }

    function academyBind(academy) {
        var academy_selector=$("#student_grade_academy");
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
        var dept_selector=$("#student_grade_dept");
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


    function gradeBind(academy,dept,grade) {
        var dept_selector=$("#student_grade_dept");
        var grade_selector=$("#student_grade_gname");
        grade_selector.html("");
        if(dept==-1){
            grade_selector.select2({
                data:[
                    {"id":-1,"text":"请选择"}
                ]
            });
            return;
        }
        $.ajax({
            url:"${APP_PATH}/grade/getGradesByAcademyAndDept",
            data:{
                "academy":academy,
                "dept": dept
            },
            type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var select_grade_body=new Array();
                var grades=result.body.grades;
                select_grade_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(grades,function (i,grade) {
                    select_grade_body[i+1]={
                        "id":grade.id,
                        "text":grade.gname
                    };
                });
                grade_selector.select2({
                    data:select_grade_body
                });
                grade_selector.val(grade).select2();
            }
        });
    }


    $("#save_btn").click(function () {

        var id=$("#id").val();
        var snum= $("input[name=student_snum]").val();
        var sname= $("input[name=student_sname]").val();
        var stel= $("input[name=student_stel]").val();
        var grade=$("#student_grade_gname").val();
        var sex=$("#student_sex").val();
        var jsonData={
            "id":id,
            "snum":snum,
            "sname":sname,
            "sex":sex,
            "stel":stel,
            "grade.id":grade
        };
        updateStudent(jsonData);
    });
    function updateStudent(jsonData) {
        $.ajax({
            url:"${APP_PATH}/student/update",
            type:"post",
            data:jsonData,
            success:function(result) {
                if(result.success==false){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                    return;
                }
                window.location.href="${APP_PATH}/views/manager/student/manageStudent.jsp";
            }
        });
    }

</script>
</body>
</html>
