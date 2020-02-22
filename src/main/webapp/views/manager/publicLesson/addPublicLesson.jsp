<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">

<head>
    <%
        String path=request.getContextPath();
        request.setAttribute("APP_PATH",path);
    %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/select2/dist/css/select2.min.css"/>">

</head>
<body>
<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/publicLesson/managePublicLesson.jsp"/>">公共课管理</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <label>日期</label>
                            <select class="form-control" id="week_select">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>时间</label>
                            <select class="form-control" id="time_select">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>教室</label>
                            <select class="form-control" id="room_select">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>课程</label>
                            <select class="form-control" id="lesson_select">
                            </select>
                        </div>
                        <button type="button" class="btn btn-success" id="add_publicLesson_btn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/tools/script/docs.min.js"/>"></script>
<script src="<c:url value="/tools/layer/layer.js" />"></script>
<script src="<c:url value="/tools/select2/dist/js/select2.min.js"/>"></script>
<script>

    $(function () {

        weekBind();
        timeBind();
        lessonBind();

        $("#room_select").select2({
            data:[
                {"id":-1,"text":"请选择"}
            ]
        });
    });

    $("#week_select").change(function () {
        roomBind();
    });
    $("#time_select").change(function () {
        roomBind();
    });


    function weekBind() {
        $("#week_select").html("");
        $.ajax({
            url:"${APP_PATH}/week/getAllWeeks",
            type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var select_weeks_body =new Array();
                var weeks=result.body.weeks;
                select_weeks_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(weeks,function (i,week) {
                    select_weeks_body[i+1]={
                        "id":week.id,
                        "text":week.term+":"+week.date
                    };
                });
                $("#week_select").select2({
                    data:select_weeks_body
                });
            }
        });
    }

    function timeBind() {
        var time_selector=$("#time_select");
        time_selector.html("");
        time_selector.select2({
            data:[
                {"id":-1,"text":"请选择"}
            ]
        });
        var weeks = $("#week_select").val();
        if(weeks==-1){
            return;
        }
        $.ajax({
            url:"${APP_PATH}/period/getAllTimes",
            type:"get",
            success:function(result) {
                var select_times_body=new Array();
                var periods=result.body.periods;
                $.each(periods,function (i,period) {
                    select_times_body[i]={
                        "id":period.id,
                        "text":period.starttime+"--"+period.endtime
                    };
                });
                time_selector.select2({
                    data:select_times_body
                })
            }
        });
    }

    function roomBind() {
        //初始化
        var room_selector=$("#room_select");
        room_selector.html("");
        room_selector.select2({
            data:[
                {"id":-1,"text":"请选择"}
            ]
        });
        var week=$("#week_select").val();
        if(week==-1){
            return;
        }
        var period = $("#time_select").val();
        if(period==-1){
            return;
        }

        $.ajax({
            url:"${APP_PATH}/availableRoom/getAvailableRoomsByWeekIdAndTimeId",
            data:{
                "weekId":week,
                "timeId":period
            },
            type:"post",
            success:function(result) {
                var select_rooms_body = new Array();
                var availableRooms=result.body.availableRooms;

                $.each(availableRooms,function (i,availableRoom) {
                    select_rooms_body[i]={
                        "id":availableRoom.id,
                        "text":availableRoom.room.building.campus+":"+availableRoom.room.building.bnum+":"+availableRoom.room.rnum
                    };
                });
                room_selector.select2({
                    data:select_rooms_body
                });
            }
        });
    }

    function lessonBind() {
        $("#lesson_select").html("");
        $.ajax({
            url:"${APP_PATH}/lesson/getAllLessons",
            type:"get",
            success:function(result) {
                var select_lesson_body =new Array();
                var lessons=result.body.lessons;
                select_lesson_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(lessons,function (i,lesson) {
                    select_lesson_body[i+1]={
                        "id":lesson.id,
                        "text":lesson.subject+"("+lesson.lnum+")"
                    };
                });
                $("#lesson_select").select2({
                    data:select_lesson_body
                });
            }
        });
    }

    $("#add_publicLesson_btn").click(function () {
        var availableRoomId= $("#room_select").val();
        var lessonId=$("#lesson_select").val();
        if(availableRoomId==-1){
            layer.msg('请选择可用教室',{time:3000,icon:5,shift:6},function () {});
            return;
        }
        if(lessonId==-1){
            layer.msg('请选择课程',{time:3000,icon:5,shift:6},function () {});
            return;
        }
        var jsonData={
            "availableRoomId":availableRoomId,
            "lessonId":lessonId,
        };
        $.ajax({
            url:"${APP_PATH}/publicLesson/validatePublicLesson",
            type:"post",
            data:{
                "availableRoomId":availableRoomId
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var flag=result.success;
                var msg=result.msg;
                //如果返回值是false则显示信息
                if(!flag){
                    layer.msg(msg,{time:3000,icon:3,shift:6});
                }else{
                    addPublicLesson(jsonData);
                }
            }
        });


    });

    function addPublicLesson(jsonData){
        $.ajax({
            url:"${APP_PATH}/publicLesson/addPublicLesson",
            data:jsonData,
            type:"post",
            beforeSend:function () {
                loadingIndex=layer.msg('提交中',{icon:16});
            },
            success:function(result) {
                var flag=result.success;
                if(flag){
                    layer.msg('提交成功',{time:3000,icon:6,shift:6},function () {});
                    window.location.href="${APP_PATH}/views/manager/publicLesson/managePublicLesson.jsp";
                }else{
                    layer.msg('提交失败，请重新提交',{time:3000,icon:5,shift:6},function () {});
                }
            }
        });
    }

</script>

</body>
</html>
