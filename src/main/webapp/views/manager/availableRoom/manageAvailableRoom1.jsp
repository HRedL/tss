<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">


<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
	<link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
	<link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">

	<%
		String path=request.getContextPath();
		request.setAttribute("APP_PATH",path);
	%>
	<title>可用教室管理</title>
</head>
<body>
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
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="update_modal_reset_btn">重置</button>
				<button type="button" class="btn btn-primary" id="update_modal_update_btn">修改</button>
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
						<div class="form-group has-feedback">
							<select class="form-control" id="query_tiaojian">
								<option value="all">查询条件</option>
								<option value="date">日期</option>
								<option value="period">时间</option>
								<option value="campus">校区</option>
								<option value="building">教学楼</option>
								<option value="room">教室</option>
							</select>
							<input class="form-control has-success" type="text" name="queryText" id="query_text_input" placeholder="请输入查询条件">
						</div>
						<button type="button" class="btn btn-warning" id="query_text_btn"><i class="glyphicon glyphicon-search"></i> 查询</button>
						<button type="button" class="btn btn-info" style="float:right;" id="one_touch_delete"><i class="glyphicon glyphicon-trash"></i> 一键删除 </button>
					</form>
					<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="del_all_btn"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button type="button" class="btn btn-primary" style="float:right;" id="add_btn"><i class="glyphicon glyphicon-plus"></i> 新增</button>

					<br>
					<hr style="clear:both;">
					<div class="table-responsive">
						<form id="user_form">
							<table class="table table-hover">
								<thead>
								<tr >
									<th width="30"><input type="checkbox" id="check_all"/></th>
									<th>日期</th>
									<th>时间</th>
									<th>校区</th>
									<th>教学楼</th>
									<th>教室</th>
									<th width="160">操作</th>
								</tr>
								</thead>
								<!--数据信息-->
								<tbody id="mainData">
								</tbody>

								<!--页码信息-->
								<tfoot>
								<tr >
									<td colspan="6" align="center">
										<!--页码信息-->
										<ul id="pageInf" class="pagination">
										</ul>
									</td>
								</tr>
								</tfoot>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/tools/script/docs.min.js"/>"></script>
<script src="<c:url value="/tools/layer/layer.js" />"></script>
<script type="text/javascript">

	var likeflag=false;

    $(function () {
        pageQuery(1);
    });
    //
    // $("#query_by_date").click(function () {
    //     var str= $("#query_by_date").html();
		// $("#query_tiaojian").html(str);
    // });



    //条件查询
    $("#query_text_btn").click(function () {
        //获取查询条件
        var queryText=$("#query_text_input").val();
        //如果有查询条件就下面就进入有查询条件的分页查询
        if(queryText==""){
            likeflag=false;
        }else {
            likeflag=true;
        }
        pageQuery(1);
    });

	//分页查询
    function pageQuery(pageNumber){

        var jsonData={
            "pageNumber":pageNumber,
            "pageSize":10
        };
        //如果要模糊查询就传输数据
        if(likeflag==true){
            jsonData.queryText=$("#query_text_input").val();
            jsonData.queryType=$("#query_tiaojian").val();
        }else{
            jsonData.queryText="";
            jsonData.queryType="all";
		}

        $.ajax({
            url:"${APP_PATH}/availableRoom/list",
            type:"get",
            data:jsonData,
            beforeSend:function () {
                loadingIndex=layer.msg('处理中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);
                var tbodyInf="";
                var pageInf="";
                var pageInfo=result.body.pageInfo;
                var availableRooms=pageInfo.list;
                var pageSize=0;
                $.each(availableRooms,function (i,availabeRoom) {
                    tbodyInf+='<tr>';
                    tbodyInf+='<td><input type="checkbox" class="check_item" value="'+availabeRoom.id+'"/></td>';
                    tbodyInf+='<td>'+availabeRoom.week.term+':'+availabeRoom.week.date+'</td>';
                    tbodyInf+='<td>'+availabeRoom.period.starttime+'--'+availabeRoom.period.endtime+'</td>';
                    tbodyInf+='<td>'+availabeRoom.room.building.campus+'</td>';
                    tbodyInf+='<td>'+availabeRoom.room.building.bnum+'</td>';
                    tbodyInf+='<td>'+availabeRoom.room.rnum+'</td>';

                    tbodyInf+='<td>';
                    tbodyInf+='<button type="button" idClass="'+availabeRoom.id+'" class="btn btn-info btn-xs detail_btn"><i class=" glyphicon glyphicon-check"></i> 详细</button>';
                    tbodyInf+='<button type="button" idClass= "'+availabeRoom.id+'" class="btn btn-warning btn-xs update_btn"><i class=" glyphicon glyphicon-pencil"></i>修改</button>';
                    tbodyInf+='<button type="button" idClass= "'+availabeRoom.id+'" class="btn btn-danger btn-xs del_btn"><i class=" glyphicon glyphicon-remove"></i>删除</button>';
                    tbodyInf+='</td>';
                    tbodyInf+='</tr>';
                    pageSize++;
                    //中止循环
                    if(pageSize==pageInfo.pageSize){
                        return false;
					}
                });

                pageInf+='<li><a href="#" onclick="pageQuery(1)">首页</a></li>';
                if(pageInfo.hasPreviousPage){
                    pageInf+='<li><a href="#" onclick="pageQuery('+(pageNumber-1)+')">上一页</a></li>';
                }

                $.each(pageInfo.navigatepageNums,function (index,item) {
                    if(item==pageNumber){
                        pageInf+='<li class="active"><a href="#">'+item+'</a></li>';
                    }else {
                        pageInf+='<li><a href="#" onclick="pageQuery('+item+')">'+item+'</a></li>';
                    }
                });

                if(pageInfo.hasNextPage){
                    pageInf+='<li><a href="#" onclick="pageQuery('+(pageNumber+1)+')">下一页</a></li>';
                }
                pageInf+='<li><a href="#" onclick="pageQuery('+pageInfo.pages+')">末页</a></li>';
                $("#mainData").html(tbodyInf);
                $("#pageInf").html(pageInf);
            }
        });
    }

    //所有单选选中，多选即选中
    $(document).on("click",".check_item",function (){
        var number=$(".check_item:checked").length;
        if(number==$(".check_item").length){
            $("#check_all").prop("checked",true);
        }else{
            $("#check_all").prop("checked",false);
        }
    });

    //给全选多选框添加点击事件
    $("#check_all").click(function(){
        var check=$(this).prop("checked");
        $(".check_item").prop("checked",check);
    });

    $("#add_btn").click(function(){
        window.location.href="${APP_PATH}/views/manager/availableRoom/addAvailableRoom.jsp";
    });



    $(document).on("click",".update_btn",function (){
        var id=$(this).attr("idClass");
        window.location.href="${APP_PATH}/views/manager/availableRoom/updateAvailableRoom.jsp?id="+id;
    });

    $(document).on("click",".detail_btn",function (){
        var id=$(this).attr("idClass");
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
            }
        });
        $("#detail_modal").modal('show');
    });

    $("#query_btn").click(function () {
        $("#query_form").submit();
    });

    $(document).on("click",".del_btn",function (){
        var id=$(this).attr("idClass");

        layer.confirm('确认要删除该条数据吗?', function(index){
            $.ajax({
                url:"${APP_PATH}/availableRoom/delete",
                type:"post",
                data:{
                    id:id
                },
                beforeSend:function () {
                    loadingIndex=layer.msg('处理中',{icon:16});
                },
                success:function(result) {
                    //只要请求发送成功，就取消加载的效果
                    layer.close(loadingIndex);

                    if(result.success){
                        //
                        layer.msg(result.msg, {time:5000,icon:1,shift:6});
                        window.location.reload();
                    }else{
                        layer.msg(result.msg, {time:5000,icon:2,shift:6});
                    }
                }

            })
        });
    });

    $("#del_all_btn").click(function () {
        var checked_item=$('input[class=check_item]:checked');

        var item=new Array();
        var number= checked_item.length;
        $.each(checked_item,function(index,value){
            item.push($(value).attr("value"));
        });

        if(number == 0){
            layer.msg('请选择删除的可用教室',{time:3000,icon:3,shift:6});
        }else{
            layer.confirm("删除已选择的可用教室信息,是否继续",{icon:3,title:"提示"},function (cindex) {
                $.ajax({
                    url:"${APP_PATH}/availableRoom/deleteAvailableRooms",
                    type:"post",
                    traditional: true,
                    data:{
                        id:item
                    },
                    success:function (result) {
                        if(result.success){
                            layer.close(cindex);
                            layer.msg('可用教室信息删除成功',{time:3000,icon:1,shift:6});
                            window.location.reload();
                        }else{
                            layer.msg('可用教室信息删除失败',{time:3000,icon:2,shift:6});
                        }
                    }
                });
            });
        }
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
                        layer.msg('批量删除失败',{time:3000,icon:1,shift:6});
                    }

                }

            });
        });

    });

</script>
</body>
</html>
