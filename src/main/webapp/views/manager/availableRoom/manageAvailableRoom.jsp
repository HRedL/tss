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
	<title>EAS-可用教室管理</title>
</head>
<body>

<%--显示详细信息的模态框--%>
<div class="modal fade bs-example-modal-lg" tabindex="-1" id="room_inf_modal" role="dialog" aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="room_inf_head"></h4>
			</div>
			<div class="modal-body">
				<table class="table table-hover table-bordered">
					<!--数据信息-->
					<tbody id="room_inf_data">
					</tbody>
				</table>
				<hr style="clear:both;">
				<table class="table table-hover table-bordered">
					<!--数据信息-->
					<tbody id="availableRoom_inf_data">
					</tbody>
				</table>
			</div>

		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--显示详细信息的模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="detail_modal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">详细信息</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="update_modal_form">
					<input id="detail_availableRoom_id" type="hidden"/>
					<input id="detail_building_id" type="hidden"/>
					<input id="detail_week_id" type="hidden"/>
					<input id="detail_time_id" type="hidden"/>
					<div class="form-group">
						<label class="col-sm-4 control-label">学期</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_week_term"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">日期</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_week_date"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">时间</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_time"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label" >校区</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_room_building_campus"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">教学楼号</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_room_building_bnum"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">教室号</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_room_rnum"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">教室容量</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_room_capacity"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">是否可用</label>
						<div class="col-sm-8">
							<select class="form-control" id="detail_availeRoom_flag">
								<option value="0">可用</option>
								<option value="1">不可用</option>
							</select>
						</div>
					</div>
				</form>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
	<div class="row">
		<jsp:include page="../common/managerMenu.jsp"/>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>可用教室管理</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" style="float:left;">
						<button type="button" class="btn btn-danger" style="float:right;" id="one_touch_add"><i class="glyphicon glyphicon-thumbs-up"></i> 一键生成</button>
						<button type="button" class="btn btn-info" style="float:right;" id="one_touch_delete"><i class="glyphicon glyphicon-trash"></i> 一键删除 </button>
					</form>
					<button type="button" class="btn btn-primary" style="float:right;" id="add_btn"><i class="glyphicon glyphicon-plus"></i> 新增</button>

					<br>
					<hr style="clear:both;">
					<div class="container-fluid">

						<div class="row">
							<div class="col-md-2">
								<label>校区</label>
							</div>
							<div class="col-md-2">
								<label>教学楼</label>
							</div>

							<div class="col-md-2 col-md-offset-2">
								<label>日期</label>
							</div>
							<div class="col-md-2">
								<label for>时间</label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-2">
								<select class="form-control" id="select_building_campus">
									<option value="-1">请选择</option>
								</select>
							</div>
							<div class="form-group col-md-2">
								<select class="form-control" id="select_building_bnum">
									<option value="-1">请选择</option>
								</select>
							</div>

							<div class="col-md-2 col-md-offset-2">
								<select class="form-control" id="select_week">
									<option value="-1">请选择</option>
								</select>
							</div>
							<div class="col-md-2">
								<select class="form-control" id="select_time">
									<option value="-1">请选择</option>
								</select>
							</div>
							<div class="col-md-2">
								<button type="button" class="btn btn-warning" id="query_btn"><i class="glyphicon glyphicon-search"></i> 查询</button>
							</div>
						</div>
						<hr style="clear:both;">

						<table class="table table-hover table-bordered">
							<!--数据信息-->
							<tbody id="mainData">
							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${APP_PATH}/tools/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/tools/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/tools/script/docs.min.js"></script>
<script src="${APP_PATH}/tools/layer/layer.js"></script>
<script src="${APP_PATH}/tools/select2/dist/js/select2.min.js"></script>
<script type="text/javascript">



    $(function () {
        initPage();
    });

    function initPage() {
        campusBind();
        weekBind();
        timeBind();
    }

    $("#select_building_campus").change(function () {
        buildingBind();
        $("#select_building_bnum").change();
    });



    $("#query_btn").click(function () {

        var campus=$("#select_building_campus").val();
        var bid = $("#select_building_bnum").val();
        var weekId=$("#select_week").val();
        var timeId=$("#select_time").val();
        // alert("这");
        if(campus==-1){
            layer.msg("请选择校区", {time:5000,icon:2,shift:6});
		}else{
            if(bid==-1){
                layer.msg("请选择教学楼", {time:5000,icon:2,shift:6});
            }else{
                if(weekId==-1){
                    roomBind(bid);
                }else{
                    availableRoomBind(bid,weekId,timeId)
                }

            }
		}

    });



    function campusBind() {
        $("#select_building_campus").html("");
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
                $("#select_building_campus").select2({
                    data:select_campus_body
                });
            }
        });
    }

    function buildingBind() {
        var bum_selector=$("#select_building_bnum");
        bum_selector.html("");
        bum_selector.select2({
            data:[
                {"id":-1,"text":"请选择"}
            ]
        });
        var campus = $("#select_building_campus").val();
        if(campus==-1){
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
                $.each(buildings,function (i,building) {
                    select_buildings_body[i]={
                        "id":building.id,
                        "text":building.bnum
                    };
                });
                bum_selector.select2({
                    data:select_buildings_body
                })
            }
        });
    }

    function weekBind() {
        $("#select_week").html("");
        $.ajax({
            url:"${APP_PATH}/week/getAllWeeks",
            type:"get",
            success:function(result) {
                var select_week_date =new Array();
                var weeks=result.body.weeks;
                select_week_date[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(weeks,function (i,week) {
                    select_week_date[i+1]={
                        id:week.id,
                        text:week.date
                    };
                });
                $("#select_week").select2({
                    data:select_week_date
                });
            }
        });
    }

    function timeBind() {
        $("#select_time").html('');
        $.ajax({
            url:"${APP_PATH}/period/getAllTimes",
            type:"get",
            success:function(result) {
                var select_time_se =new Array();
                var periods=result.body.periods;
                select_time_se[0]={
                    "id":-1,
                    "text":"请选择"
                };
                $.each(periods,function (i,period) {
                    select_time_se[i+1]={
                        id:period.id,
                        text:period.starttime+"----"+period.endtime
                    };
                });
                $("#select_time").select2({
                    data:select_time_se
                });
            }
        });
    }

    function availableRoomBind(bid,weekId,timeId){


        $.ajax({
            url:"${APP_PATH}/availableRoom/getAvailableRoomsByBuildingAndTime",
            data:{
              	"bid":bid,
				"weekId":weekId,
				"timeId":timeId
            },
            type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var tbodyInf="";
                var availableRooms=result.body.availableRooms;

                var roomsInf=[
                    ['101','102','103','104','105','106','107','108','109','110','111','112','113'],
                    ['201','202','203','204','205','206','207','208','209','210','211','212','213'],
                    ['301','302','303','304','305','306','307','308','309','310','311','312','313'],
                    ['401','402','403','404','405','406','407','408','409','410','411','412','413'],
                    ['501','502','503','504','505','506','507','508','509','510','511','512','513']
                ];


                var count=0;

                $.each(roomsInf,function (i,floor) {
                    tbodyInf+='<tr>';
                    $.each(floor,function (j,room) {
                        if(availableRooms[count]!=null&&room==availableRooms[count].room.rnum){
                            if(availableRooms[count].flag==0){
                                tbodyInf+='<td class="info">';
                                tbodyInf+='<a href="#" onclick="availableRoomClick1('+availableRooms[count].id+')">';
                                tbodyInf+=room;
                                tbodyInf+='</a>';
                                tbodyInf+='</td>';
							}else{
                                tbodyInf+='<td class="danger">';
                                tbodyInf+='<a href="#" onclick="availableRoomClick1('+availableRooms[count].id+')">';
                                tbodyInf+=room;
                                tbodyInf+='</a>';
                                tbodyInf+='</td>';

							}

                            count++;
                        }else{
                            tbodyInf+='<td>';
                            tbodyInf+=room;
                            tbodyInf+='</td>';
                        }
                    });
                    tbodyInf+='</tr>';
                });

                $("#mainData").html(tbodyInf);

            }
		});
	}

    function availableRoomClick1(id){
        $.ajax({
            url:"${APP_PATH}/availableRoom/detail",
            type:"get",
            data:{
                id:id
            },
            success:function(result) {
                var availableRoom=result.body.availableRoom;
                var detail_time=availableRoom.period.starttime;
                detail_time+="----";
                detail_time+=availableRoom.period.endtime;
                $("#detail_week_term").html(availableRoom.week.term);
                $("#detail_week_date").html(availableRoom.week.date);
                $("#detail_time").html(detail_time);
                $("#detail_room_building_campus").html(availableRoom.room.building.campus);
                $("#detail_room_building_bnum").html(availableRoom.room.building.bnum);
                $("#detail_room_rnum").html(availableRoom.room.rnum);
                $("#detail_room_capacity").html(availableRoom.room.capacity);
                $("#detail_availeRoom_flag").val(availableRoom.flag);
                $("#detail_availableRoom_id").val(availableRoom.id);
                $("#detail_building_id").val(availableRoom.room.building.id);
                $("#detail_week_id").val(availableRoom.week.id);
                $("#detail_time_id").val(availableRoom.period.id);
            }
        });
        $("#detail_modal").modal('show');

	}



    function roomBind(bid){

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
                var tbodyInf="";
                var rooms=result.body.rooms;
                var roomsInf=[
                    ['101','102','103','104','105','106','107','108','109','110','111','112','113'],
                    ['201','202','203','204','205','206','207','208','209','210','211','212','213'],
                    ['301','302','303','304','305','306','307','308','309','310','311','312','313'],
                    ['401','402','403','404','405','406','407','408','409','410','411','412','413'],
                    ['501','502','503','504','505','506','507','508','509','510','511','512','513']
                ];
				var count=0;

                $.each(roomsInf,function (i,floor) {
                    tbodyInf+='<tr>';
                    $.each(floor,function (j,room) {
                        if(rooms[count]!=null&&room==rooms[count].rnum){
                            tbodyInf+='<td class="active">';
                            tbodyInf+='<a href="#" onclick="roomClick('+rooms[count].id+')">';
                            tbodyInf+=room;
                            tbodyInf+='</a>';
                            tbodyInf+='</td>';
                            count++;
                        }else{
                            tbodyInf+='<td>';
                            tbodyInf+=room;
                            tbodyInf+='</td>';
						}
                    });
                    tbodyInf+='</tr>';
                });

                $("#mainData").html(tbodyInf);

            }
        });

	}
	function roomClick(rid){

        $("#availableRoom_inf_data").html('');

        $.ajax({
            url:"${APP_PATH}/availableRoom/getAvailableRoomByRid",
            data:{
                "rid": rid
            },
            type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('加载中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var availableRooms=result.body.availableRooms;
                var weeks=result.body.weeks;
                var periods=result.body.periods;
                var tbodyInf="";
                tbodyInf+='<tr><td>时间/日期</td>';
                $.each(weeks,function (i,week) {
                    tbodyInf+='<td>'+week.date+'</td>';
                });
                tbodyInf+='</tr>';

                $.each(periods,function (i,period) {
                    tbodyInf+='<tr><td>'+period.starttime+'--'+period.endtime+'</td>';
                    $.each(availableRooms,function (i,availableRoom) {
                      if(period.id==availableRoom.period.id){
                          if(availableRoom.flag==0){

                              tbodyInf+='<td>';
                              tbodyInf+='<a href="#" onclick="availableRoomClick('+availableRoom.id+','+availableRoom.room.id+')">';
                              tbodyInf+='可用';
                              tbodyInf+='</a>';

                              tbodyInf+='</td>';
						  }else{
                              tbodyInf+='<td>';
                              tbodyInf+='<a href="#" onclick="availableRoomClick('+availableRoom.id+','+availableRoom.room.id+')">';
                              tbodyInf+='不可用';
                              tbodyInf+='</a>';
						  }
					  }
                    });
                    tbodyInf+='</tr>';
                });
                $("#room_inf_data").html(tbodyInf);
            }
		});

        $("#room_inf_modal").modal('show');
	}




	function availableRoomClick(availableRoomId,roomId){
		var tbodyInf='';
        $.ajax({
            url:"${APP_PATH}/availableRoom/detail",
            data:{
                "id": availableRoomId
            },
            type:"get",
            success:function(result) {
                var availableRoom=result.body.availableRoom;
                tbodyInf+='<form>';

                tbodyInf+='<input type="hidden" id="availableRoomId_modal" value="'+availableRoomId+'"/>';

                tbodyInf+='<div class="form-group">';
                tbodyInf+='<label class="col-sm-2 control-label">日期</label>';
                tbodyInf+='<div class="col-sm-4">';
                tbodyInf+=' <p class="form-control-static">'+availableRoom.week.date+'</p>';
                tbodyInf+='</div>';
                tbodyInf+='<form class="form-horizontal">';
                tbodyInf+='<div class="form-group">';
                tbodyInf+='<label class="col-sm-2 control-label">日期</label>';
                tbodyInf+='<div class="col-sm-4">';
                tbodyInf+=' <p class="form-control-static">'+availableRoom.period.starttime+'--'+availableRoom.period.endtime+'</p>';
                tbodyInf+='</div>';

                tbodyInf+='<div class="form-group">';
                tbodyInf+='<label class="col-sm-2 control-label">教室位置</label>';
                tbodyInf+='<div class="col-sm-4">';
                tbodyInf+=' <p class="form-control-static">'+availableRoom.room.building.campus+' : '+availableRoom.room.building.bnum+availableRoom.room.rnum+'</p>';
                tbodyInf+='</div>';

                tbodyInf+='<div class="form-group">';
                tbodyInf+='<label class="col-sm-2 control-label">教室容量</label>';
                tbodyInf+='<div class="col-sm-4">';
                tbodyInf+='<p class="form-control-static">'+availableRoom.room.capacity+'</p>';
                tbodyInf+='</div>';
                tbodyInf+='</div>';
                tbodyInf+='</form>';

                tbodyInf+='<div class="form-group">';
                tbodyInf+='<select roomIdClass="'+roomId+'" class="form-control" id="availableRoom_flag">';
                tbodyInf+='<option value="0">';
                tbodyInf+='可用';
                tbodyInf+='</option>';
                tbodyInf+='<option value="1">';
                tbodyInf+='不可用';
                tbodyInf+='</option>';
                tbodyInf+='</select>';
                tbodyInf+='</div>';
                tbodyInf+='</form>';
                $("#availableRoom_inf_data").html(tbodyInf);

                $("#availableRoom_flag").val(availableRoom.flag);
            }
		});



        $("#availableRoom_inf_data").html(body);
    }

    $("#detail_availeRoom_flag").change(function(){
        var id=$("#detail_availableRoom_id").val();
        var flag=$("#detail_availeRoom_flag").val();
        $.ajax({
            url:"${APP_PATH}/availableRoom/update",
            data:{
                "id":id,
                "flag":flag
            },
            type:"post",
            success:function(result) {
                $("#detail_modal").modal('hide');
                var bid=$("#detail_building_id").val();
                var weekId=$("#detail_week_id").val();
                var timeId=$("#detail_time_id").val();
                availableRoomBind(bid,weekId,timeId);

            }
        });


	});

    $(document).on("change","#availableRoom_flag",function (){
        var id= $("#availableRoomId_modal").val();
		var flag=$("#availableRoom_flag").val();
        $.ajax({
            url:"${APP_PATH}/availableRoom/update",
            data:{
                "id":id,
				"flag":flag
            },
            type:"post",
            success:function(result) {
               // var roomId= $("#avaiableRoom_flag").attr("roomIdClass");

                $("#room_inf_modal").modal('hide');

            }
		});
    });


    $("#add_btn").click(function(){
        window.location.href="${APP_PATH}/views/manager/availableRoom/addAvailableRoom.jsp";
    });




    $("#one_touch_add").click(function () {

        layer.confirm("自动生成可用教室信息,是否继续",{icon:3,title:"提示"},function (cindex) {
		$.ajax({
			url:"${APP_PATH}/availableRoom/generateAvailableRoom",
			type:"get",
            beforeSend:function () {
                loadingIndex=layer.msg('处理中',{icon:16});
            },
            success:function(result) {
			    layer.close(loadingIndex);
			    if(result.success){
                    layer.msg('批量插入成功',{time:3000,icon:1,shift:6});
                    pageQuery(1);
                }else{
                    layer.msg(result.msg,{time:3000,icon:2,shift:6});
                }
            }
		});
		});
    });

    $("#one_touch_delete").click(function () {


        layer.confirm("删除所有可用教室信息,是否继续",{icon:3,title:"提示"},function (cindex) {
            $.ajax({
                url:"${APP_PATH}/availableRoom/deleteAllAvailableRoom",
                type:"get",
                beforeSend:function () {
                    loadingIndex=layer.msg('处理中',{icon:16});
                },
                success:function(result) {
                    layer.close(loadingIndex);
                    if(result.success){
                        layer.msg('批量删除成功',{time:3000,icon:1,shift:6});
                        pageQuery(1);
                    }else{
                        layer.msg('批量删除失败',{time:3000,icon:2,shift:6});
                    }

                }

            });
        });

    });

</script>
</body>
</html>
