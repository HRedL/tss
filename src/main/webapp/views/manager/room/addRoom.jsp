<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/12/29
  Time: 15:10
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
    <link rel="stylesheet" href="<c:url value="/tools/select2/dist/css/select2.min.css"/>">
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
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <label >校区</label>
                            <select class="form-control" id="room_building_campus">
                            </select>
                        </div>
                        <div class="form-group">
                            <label >教学楼</label>
                            <select class="form-control" id="room_building_bnum">
                            </select>
                        </div>
                        <div class="form-group">
                            <label>教室号</label>
                            <input name="room_rnum" class="form-control" placeholder="请输入教室号"/>
                        </div>
                        <div class="form-group">
                            <label>教室容量</label>
                            <input name="room_capacity" class="form-control"  placeholder="请输入教室容量"/>
                        </div>
                        <div class="form-group">
                            <label>所需监考教师人数</label>
                            <input name="room_ttotalnum" class="form-control"  placeholder="请输入所需监考人数"/>
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
<script src="<c:url value="/tools/script/docs.min.js"/>"></script>
<script src="<c:url value="/tools/layer/layer.js" />"></script>
<script src="<c:url value="/tools/select2/dist/js/select2.min.js"/>"></script>
<script >

    $(function () {
        initAddPage();
    });

    $("#room_building_campus").change(function () {
        var campus = $("#room_building_campus").val();
        buildingBind(campus,-1);
    });

    function initAddPage() {
        campusBind(-1);
        buildingBind(-1);
    }


    function campusBind(campus) {
        var campus_selector=$("#room_building_campus");
        campus_selector.html("");
        $.ajax({
            url:"${APP_PATH}/dict/getDictsByType",
            data:{
                "type": "campus"
            },
            type:"get",
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
        var bum_selector=$("#room_building_bnum");
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

    $("#save_btn").click(function () {
        var bnum=$("#room_building_bnum").val();
        var rnum=$("input[name=room_rnum]").val();
        var capacity= $("input[name=room_capacity]").val();
        var ttotalnum=$("input[name=room_ttotalnum]").val();

        var jsonData={
            "building.id":bnum,
            "rnum":rnum,
            "capacity":capacity,
            "ttotalnum":ttotalnum
        };

        addRoom(jsonData);
    });

    function addRoom(jsonData){
        $.ajax({
            url:"${APP_PATH}/room/add",
            data:jsonData,
            type:"post",
            beforeSend:function () {
                loadingIndex=layer.msg('提交中',{icon:16});
            },
            success:function(result) {
                var flag=result.success;
                if(flag){
                    layer.msg('提交成功',{time:3000,icon:6,shift:6},function () {});
                    window.location.href="${APP_PATH}/views/manager/room/manageRoom.jsp";
                }else{
                    layer.msg('提交失败，请重新提交',{time:3000,icon:5,shift:6},function () {});
                }
            }
        });
    }


</script>
</body>
</html>

