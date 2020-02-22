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
    <title>可用教室管理</title>
</head>
<body>
<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/availableRoom/manageAvailableRoom.jsp"/>">可用教室管理</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form" id="add_availableRoom_form">
                        <input type="hidden" id="availableRoomId" value="${param.id}"/>
                        <div class="form-group">
                            <label>日期</label>
                            <select class="form-control" id="select_week_date">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>时间</label>
                            <select class="form-control" id="select_time_se">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>校区</label>
                            <select class="form-control" id="campus_select">
                            </select>
                        </div>

                        <div class="form-group">
                            <label>教学楼号</label>
                            <select class="form-control" id="bum">
                            </select>
                        </div>

                        <div class="form-group">
                            <label>教室号</label>
                            <select class="form-control" id="rnum">
                            </select>
                        </div>

                        <button type="button" class="btn btn-success" id="add_availableRoom_btn"><i class="glyphicon glyphicon-plus"></i> 提交</button>
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

    //因为有多级联动，所以肯定要用ajax，所以就不不伦不类了，都统一用ajax来搞把
    $(function () {
        initPage();
    });

    $("#campus_select").change(function () {
        var campus = $("#campus_select").val();
        buildingBind(campus,-1);
        $("#bum").change();
    });
    $("#bum").change(function () {
        var bid = $("#bum").val();
        roomBind(bid,-1);
    });

    function initPage() {
        var id= $("#availableRoomId").val();
        $.ajax({
            url:"${APP_PATH}/availableRoom/initUpdatePage",
            type:"post",
            data:{
                "id":id
            },
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var availableRoom=result.body.availableRoom;

                dateBind(availableRoom.week.id);
                timeBind(availableRoom.period.id);
                alert(availableRoom.room.building.campus);
                campusBind(availableRoom.room.building.campus);
                buildingBind(availableRoom.room.building.campus,availableRoom.room.building.id);
                roomBind(availableRoom.room.building.id,availableRoom.room.id);

            }
        });
    }


    function dateBind(defaultWeek) {
        $.ajax({
            url:"${APP_PATH}/week/getAllWeeks",
            type:"get",
            success:function(result) {
                var select_week_date =new Array();
                var weeks=result.body.weeks;
                $.each(weeks,function (i,week) {
                    select_week_date[i]={
                        id:week.id,
                        text:week.date
                    };
                });
                var week_date_selector=$("#select_week_date");
                week_date_selector.select2({
                    data:select_week_date
                });
                week_date_selector.val(defaultWeek).select2();
            }
        });
    }

    function timeBind(defaultTime) {
        $.ajax({
            url:"${APP_PATH}/period/getAllTimes",
            type:"get",
            success:function(result) {
                var select_time_se =new Array();
                var periods=result.body.periods;
                $.each(periods,function (i,period) {
                    select_time_se[i]={
                        id:period.id,
                        text:period.starttime+"----"+period.endtime
                    };
                });
                var time_se_selector=$("#select_time_se");
                time_se_selector.select2({
                    data:select_time_se
                });
                time_se_selector.val(defaultTime).select2();
            }
        });
    }



    function campusBind(campus) {
        var campus_selector=$("#campus_select");
        campus_selector.html("");
        $.ajax({
            url:"${APP_PATH}/dict/getDictsByType",
            data:{
                "type": "campus"
            },
            type:"post",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var select_campus_body =new Array();
                var dicts=result.body.dicts;
                select_campus_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(dicts,function (i,dict) {
                    select_campus_body[i+1]={
                        "id":dict.value,
                        "text":dict.label
                    };
                });
                campus_selector.select2({
                    data:select_campus_body
                });
                campus_selector.val(campus).select2();
            }
        });
    }

    function buildingBind(campus,bid) {
        var bum_selector=$("#bum");
        bum_selector.html("");
        if(campus==-1){
            bum_selector.select2({
                data:[
                    {"id":-1,"text":"请选择"}
                ]
            });
            return;
        }
        $.ajax({
            url:"${APP_PATH}/building/getBuildingByCampus",
            data:{
                "campus": campus
            },
            type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var select_buildings_body=new Array();
                var buildings=result.body.buildings;
                select_buildings_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(buildings,function (i,building) {
                    select_buildings_body[i+1]={
                        "id":building.id,
                        "text":building.bnum
                    };
                });
                bum_selector.select2({
                    data:select_buildings_body
                });
                bum_selector.val(bid).select2();
            }
        });
    }

    function roomBind(bid,rid) {
        //初始化

        var rnum_selector=$("#rnum");
        rnum_selector.html("");
        if(bid==-1){
            rnum_selector.select2({
                data:[
                    {"id":-1,"text":"请选择"}
                ]
            });
            return;
        }
        $.ajax({
            url:"${APP_PATH}/room/getRoomsByBid",
            data:{
                "bid": bid
            },
            type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var select_rooms_body = new Array();
                var rooms=result.body.rooms;
                select_rooms_body[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(rooms,function (i,room) {
                    select_rooms_body[i+1]={
                        "id":room.id,
                        "text":room.rnum
                    };
                });
                rnum_selector.select2({
                    data:select_rooms_body
                });
                rnum_selector.val(rid).select2();
            }
        });
    }


    $("#add_availableRoom_btn").click(function () {
        var weekid= $("#week_date").val();
        var timeid=$("#time_se").val();
        var rid=$("#rnum").val();
        var id=$("#availableRoomId").val();
        if(rid==-1){
            layer.msg('请选择教室',{time:3000,icon:5,shift:6},function () {});
            return;
        }
        var jsonData={
            "weekid":weekid,
            "timeid":timeid,
            "rid":rid
        };
        var jsonData1={
            "id":id,
            "weekid":weekid,
            "timeid":timeid,
            "rid":rid
        };
        $.ajax({
            url:"${APP_PATH}/availableRoom/validateAvailableRoom",
            type:"post",
            data:jsonData,
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var flag=result.success;
                var msg=result.msg;
                //如果返回值是false则显示信息
                if(!flag){
                    if(result.body.availableRoom.id==id){
                        layer.msg("没有进行修改",{time:3000,icon:3,shift:6});
                    }else{
                        layer.msg(msg,{time:3000,icon:3,shift:6});
                    }

                }else{
                    updateAvailableRoom(jsonData1);
                }
            }
        });


    });

    function updateAvailableRoom(jsonData){
        $.ajax({
            url:"${APP_PATH}/availableRoom/updateAvailableRoom",
            data:jsonData,
            type:"post",
            beforeSend:function () {
                loadingIndex=layer.msg('提交中',{icon:16});
            },
            success:function(result) {
                var flag=result.success;
                if(flag){

                    layer.msg('提交成功',{time:3000,icon:6,shift:6},function () {});
                    window.location.href="${APP_PATH}/views/manager/availableRoom/manageAvailableRoom.jsp";
                }else{
                    layer.msg('提交失败，请重新提交',{time:3000,icon:5,shift:6},function () {});
                }
            }
        });
    }

</script>

</body>
</html>
