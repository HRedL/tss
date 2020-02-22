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
                      <input type="hidden" id="id" value="${param.id}"/>

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
                        <button type="button" class="btn btn-success" id="update_publicLesson_btn"><i class="glyphicon glyphicon-plus"></i> 提交</button>
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
        initPage();
    });

    //初始化界面，即加载本条publicLesson原本的信息
    function initPage() {
        var id=$("#id").val();
        $.ajax({
            url:"${APP_PATH}/publicLesson/getPublicLesson",
            type:"get",
            data:{
                "id":id
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var publicLesson=result.body.publicLesson;
                weekBind(publicLesson.availableRoom.week.id);
                timeBind(publicLesson.availableRoom.period.id);
                roomBind(publicLesson.availableRoom.week.id,publicLesson.availableRoom.period.id,publicLesson.availableRoom.id);
                lessonBind(publicLesson.lesson.id);
                $("#room_select").attr("default_value",publicLesson.availableRoom.id);
            }

        });
    }

    //
    $("#week_select").change(function () {
        roomBind(-1);
    });

    $("#time_select").change(function () {

        var week=$("#week_select").val();
        if(week==-1){
            return;
        }
        var period = $("#time_select").val();
        if(period==-1){
            return;
        }
        roomBind(week,period,-1);
    });

    //改变weekBind下拉列表的函数
    function weekBind(weekId) {
        var week_selector=$("#week_select");

        week_selector.html("");
        $.ajax({
            url:"${APP_PATH}/week/getAllWeeks",
            type:"get",
            success:function(result) {
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
                week_selector.select2({
                    data:select_weeks_body
                });
                week_selector.val(weekId).select2();
            }
        });
    }

    //改变time下拉列表的函数
    function timeBind(timeId) {
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
                });
                time_selector.val(timeId).select2();

            }
        });

    }

    //改变room下拉列表的函数
    function roomBind(week,period,roomId) {
        //初始化
        var room_selector=$("#room_select");
        room_selector.html("");
        room_selector.select2({
            data:[
                {"id":-1,"text":"请选择"}
            ]
        });


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

                room_selector.val(roomId).select2();
            }
        });
    }

    //改变lesson下拉列表的函数
    function lessonBind(lessonId) {

        var lesson_selector=$("#lesson_select");
        lesson_selector.html("");
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
                lesson_selector.select2({
                    data:select_lesson_body
                });

                lesson_selector.val(lessonId).select2();
            }
        });
    }

    //提交按钮被点击，判断后提交信息
    $("#update_publicLesson_btn").click(function () {
        var room_selector=$("#room_select");
        var availableRoomId= room_selector.val();
        var lessonId=$("#lesson_select").val();
        var id=$("#id").val();
        var default_availableRoomId=room_selector.attr("default_value");
        if(availableRoomId==-1){
            layer.msg('请选择可用教室',{time:3000,icon:5,shift:6},function () {});
            return;
        }
        if(lessonId==-1){
            layer.msg('请选择课程',{time:3000,icon:5,shift:6},function () {});
            return;
        }
        var jsonData={
            "id":id,
            "availableRoomId":availableRoomId,
            "lessonId":lessonId,
        };
        if(default_availableRoomId!=availableRoomId){

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
                    var success = result.success;
                    var msg = result.msg;
                    if (success == false) {
                        layer.msg(msg, {time: 3000, icon: 3, shift: 6});
                    } else {
                        updatePublicLesson(jsonData);
                    }
                }
            });

        }else{
            updatePublicLesson(jsonData);
        }


    });

    //提交修改信息的函数
    function updatePublicLesson(jsonData){
        $.ajax({
            url:"${APP_PATH}/publicLesson/updatePublicLesson",
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
