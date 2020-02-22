<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">


<head>
	<title>EAS-公共课管理</title>
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
	<link rel="stylesheet" href="${APP_PATH}/tools/file_input/css/fileinput.min.css"/>
	<link rel="stylesheet" href="${APP_PATH}/tools/select2/dist/css/select2.min.css">


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
						<label class="col-sm-4 control-label">课程号</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_lesson_lnum"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">课程名</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_lesson_lname"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">班级名</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_lesson_grades"></p>
						</div>
					</div>
				</form>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--上传文件的模态框--%>
<div class="modal fade" tabindex="-1" role="dialog" id="import_modal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">导入文件</h4>
			</div>
			<div class="modal-body">
				<form  action="${APP_PATH}/upload" method="post"  enctype="multipart/form-data">
					<a href="" class="form-control" style="border:none;">下载导入模板</a>

					<input type="file" name="file" id="file" class="file-loading" />
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
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>公共课管理</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" style="float:left;">
						<div class="form-group has-feedback">
							<div class="input-group">
								<div class="input-group-addon">查询条件</div>
								<input class="form-control has-success" type="text" name="queryText" id="query_text_input" placeholder="请输入查询条件">
							</div>
						</div>
						<button type="button" class="btn btn-warning" id="query_text_btn"><i class="glyphicon glyphicon-search"></i> 查询</button>
						<button type="button" class="btn btn-info" style="float:right;" id="one_touch_delete"><i class="glyphicon glyphicon-trash"></i> 一键删除 </button>
                        <button type="button" class="btn btn-danger" style="float:right;" id="one_touch_room"><i class="glyphicon glyphicon-refresh"></i>更新可用教室 </button>
                        <button type="button" class="btn btn-success" style="float:right;" id="one_touch_exam"><i class="glyphicon glyphicon-log-in"></i>插入考次表 </button>
					</form>
					<button type="button" class="btn btn-primary" style="float:right;margin-left:10px;margin-top: -10px" id="import_publicLesson"><i class=" glyphicon glyphicon-plus"></i> Excel导入</button>
					<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;margin-top: -10px" id="del_all_btn"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button type="button" class="btn btn-primary" style="float:right;margin-top: -10px" id="add_btn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
					<br>
					<hr style="clear:both;">
					<div class="table-responsive">
						<form id="user_form">
							<table class="table table-hover">
								<thead>
								<tr >
									<th width="30"><input type="checkbox" id="check_all"/></th>
									<th width="80">
										<select class="form-control" id="publicLesson_availableRoom_week">
											<option value="-1">日期</option>
										</select>
									</th>
									<th width="80">
										<select class="form-control" id="publicLesson_availableRoom_time">
											<option value="-1">时间</option>
										</select>
									</th>
									<th width="140">
										<select class="form-control" id="publicLesson_availableRoom_room">
											<option value="-1">考试地点</option>
										</select>
									</th>
									<th width="160">
										<select class="form-control" id="publicLesson_lesson" >
											<option value="-1">考试科目</option>
										</select>
									</th>
									<th width="180">操作</th>
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
<script src="${APP_PATH}/tools/file_input/js/fileinput.min.js"></script>
<script src="${APP_PATH}/tools/file_input/js/locales/zh.js"></script>
<script src="${APP_PATH}/tools/select2/dist/js/select2.min.js"></script>


<script type="text/javascript">

    var likeflag=false;

    $(function () {
        pageQuery(1);
        initPage();
    });

    function initPage(){
		weekBind();
		timeBind();
		roomBind();
		lessonBind();
	}


    $("#publicLesson_availableRoom_week").change(function () {
        select_change();
    });
    $("#publicLesson_availableRoom_time").change(function () {
        select_change();
    });

    $("#publicLesson_availableRoom_room").change(function () {
        select_change();
    });

    $("#publicLesson_lesson").change(function () {
        select_change();
    });


    function select_change() {

		var week= $("#publicLesson_availableRoom_week").val();
		var period= $("#publicLesson_availableRoom_time").val();
		var room= $("#publicLesson_availableRoom_room").val();
		var lesson= $("#publicLesson_lesson").val();
		criteriaQuery(1,10,week,period,room,lesson);
    }

    function weekBind() {
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
                $("#publicLesson_availableRoom_week").select2({
                    data:select_week_date
                });
            }
        });
    }

    function timeBind() {
        $.ajax({
            url:"${APP_PATH}/period/getAllTimes",
            type:"get",
            success:function(result) {
                var select_time_se =new Array();
                var periods=result.body.periods;
                $.each(periods,function (i,period) {
                    select_time_se[i]={
                        id:period.id,
                        text:period.starttime+"--"+period.endtime
                    };
                });
                $("#publicLesson_availableRoom_time").select2({
                    data:select_time_se
                });
            }
        });
    }

    function roomBind() {
        $.ajax({
            url:"${APP_PATH}/room/getAllRooms",
            type:"get",
            success:function(result) {
                var select_rooms_body = new Array();
                var rooms=result.body.rooms;
                $.each(rooms,function (i,room) {
                    select_rooms_body[i]={
                        "id":room.id,
                        "text":room.building.campus+':'+room.building.bnum+':'+room.rnum
                    };
                });

                $("#publicLesson_availableRoom_room").select2({
                    data:select_rooms_body
                });
            }
        });
    }

    function lessonBind() {
        $.ajax({
            url:"${APP_PATH}/lesson/getAllLessons",
            type:"get",
            success:function(result) {
                var select_lesson_body =new Array();
                var lessons=result.body.lessons;

                $.each(lessons,function (i,lesson) {
                    select_lesson_body[i]={
                        "id":lesson.id,
                        "text":lesson.subject+"("+lesson.lnum+")"
                    };
                });
                $("#publicLesson_lesson").select2({
                    data:select_lesson_body
                });
            }
        });
    }

    function criteriaQuery(pageNumber,pageSize,weekId,timeId,roomId,lessonId){
        $.ajax({
            url:"${APP_PATH}/publicLesson/criteriaQuery",
            type:"get",
			data:{
                pageNumber:pageNumber,
                pageSize:10,
                weekId:weekId,
				timeId:timeId,
				roomId:roomId,
				lessonId:lessonId,
            },
            beforeSend:function () {
                loadingIndex=layer.msg('处理中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);

                if(result.success==false){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                    return;
                }
                var tbodyInf="";
                var pageInf="";
                var pageInfo=result.body.pageInfo;
                var publicLessons=pageInfo.list;
                var pageSize=0;
                $.each(publicLessons,function (i,publicLesson) {
                    tbodyInf+='<tr>';
                    tbodyInf+='<td><input type="checkbox" class="check_item" value="'+publicLesson.id+'"/></td>';
                    tbodyInf+='<td>'+publicLesson.availableRoom.week.date+'</td>';
                    tbodyInf+='<td>'+publicLesson.availableRoom.period.starttime+'--'+publicLesson.availableRoom.period.endtime+'</td>';
                    tbodyInf+='<td>'+publicLesson.availableRoom.room.building.bnum+':'+publicLesson.availableRoom.room.rnum+'</td>';
                    tbodyInf+='<td>'+publicLesson.lesson.subject+'</td>';
                    tbodyInf+='<td>';
                    tbodyInf+='<button type="button" idClass="'+publicLesson.id+'" class="btn btn-info btn-xs detail_btn"><i class=" glyphicon glyphicon-check"></i> 详细</button>';
                    tbodyInf+='<button type="button" idClass= "'+publicLesson.id+'" class="btn btn-warning btn-xs update_btn"><i class=" glyphicon glyphicon-pencil"></i>修改</button>';
                    tbodyInf+='<button type="button" idClass= "'+publicLesson.id+'" class="btn btn-danger btn-xs del_btn"><i class=" glyphicon glyphicon-remove"></i>删除</button>';
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
        var week= $("#publicLesson_availableRoom_week").val();
        var period= $("#publicLesson_availableRoom_time").val();
        var room= $("#publicLesson_availableRoom_room").val();
        var lesson= $("#publicLesson_lesson").val();

		criteriaQuery(pageNumber,10,week,period,room,lesson);
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
        window.location.href="${APP_PATH}/views/manager/publicLesson/addPublicLesson.jsp";
    });

    $(document).on("click",".update_btn",function (){
        var id=$(this).attr("idClass");
        window.location.href="${APP_PATH}/views/manager/publicLesson/updatePublicLesson.jsp?id="+id;
    });

    $(document).on("click",".detail_btn",function (){
        var id=$(this).attr("idClass");
        $.ajax({
            url:"${APP_PATH}/publicLesson/detail",
            type:"get",
            data:{
                id:id
            },
            success:function(result) {
                var publicLesson=result.body.publicLesson;
                var availableRoom=publicLesson.availableRoom;
                var detail_time=availableRoom.period.starttime;
                detail_time+="----";
                detail_time+=availableRoom.period.endtime;
                var detail_lesson_grades=""
                $.each(publicLesson.lesson.grades,function(i,grade){
                    detail_lesson_grades+=grade.gname+";";
                });

                $("#detail_week_term").html(availableRoom.week.term);
                $("#detail_week_date").html(availableRoom.week.date);
                $("#detail_time").html(detail_time);
                $("#detail_room_building_campus").html(availableRoom.room.building.campus);
                $("#detail_room_building_bnum").html(availableRoom.room.building.bnum);
                $("#detail_room_rnum").html(availableRoom.room.rnum);
                $("#detail_lesson_lnum").html(publicLesson.lesson.lnum);
                $("#detail_lesson_lname").html(publicLesson.lesson.subject);
                $("#detail_lesson_grades").html(detail_lesson_grades);
            }
        });
        $("#detail_modal").modal('show');
    });


    $(document).on("click",".del_btn",function (){
        var id=$(this).attr("idClass");

        layer.confirm('确认要删除该条数据吗?', function(index){
            $.ajax({
                url:"${APP_PATH}/publicLesson/delete",
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
                    layer.close(index);

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
            layer.msg('请选择删除的公共课',{time:3000,icon:3,shift:6});
        }else{
            layer.confirm("删除已选择的公共课信息,是否继续",{icon:3,title:"提示"},function (cindex) {
                $.ajax({
                    url:"${APP_PATH}/publicLesson/deletePublicLessons",
                    type:"post",
                    traditional: true,
                    data:{
                        id:item
                    },
                    success:function (result) {
                        if(result.success){
                            layer.close(cindex);
                            layer.msg('公共课信息删除成功',{time:3000,icon:1,shift:6});
                            window.location.reload();
                        }else{
                            layer.msg('公共课信息删除失败',{time:3000,icon:2,shift:6});
                        }
                    }
                });
            });
        }
    });

    $("#one_touch_delete").click(function () {
        layer.confirm("删除所有公共课信息,是否继续",{icon:3,title:"提示"},function (cindex) {
            $.ajax({
                url:"${APP_PATH}/publicLesson/deleteAll",
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

    $("#one_touch_room").click(function () {
        layer.confirm("更新可用教室,是否继续",{icon:3,title:"提示"},function (cindex) {
            $.ajax({
                url:"${APP_PATH}/publicLesson/updateRoom",
                type:"get",
                beforeSend:function () {
                    loadingIndex=layer.msg('处理中',{icon:16});
                },
                success:function(result) {
                    layer.close(loadingIndex);
                    if(result.success==true){
                        layer.msg('更新可用教室成功',{time:3000,icon:1,shift:6});
                        pageQuery(1);
                    }else{
                        layer.msg('更新可用教室失败',{time:3000,icon:1,shift:6});
                    }
                }
            });
        });
    });

    $("#one_touch_exam").click(function () {
        layer.confirm("将该表插入考次表,是否继续",{icon:3,title:"提示"},function (cindex) {
            $.ajax({
                url:"${APP_PATH}/publicLesson/insertToExam",
                type:"post",
                beforeSend:function () {
                    loadingIndex=layer.msg('处理中',{icon:16});
                },
                success:function(result) {
                    layer.close(loadingIndex);
                    if(result.success==true){
                        layer.msg('插入成功',{time:3000,icon:1,shift:6});
                        pageQuery(1);
                    }else{
                        layer.msg('插入失败',{time:3000,icon:1,shift:6});
                    }
                }
            });
        });
    });

    //下载导入模板
    function downloadFileByForm() {
        var url = "${APP_PATH}/upexportPloadFile/publicLesson";
        var fileName = "publicLesson.xlsx";
        var form = $("<form></form>").attr("action", url).attr("method", "post");
        form.append($("<input></input>").attr("type", "hidden").attr("name", "filename").attr("value", fileName));
        form.appendTo('body').submit().remove();
    }


    //导入文件
    $("#import_publicLesson").click(function () {
        $("#import_modal").modal('show');
        initFileInput("file");
    });

    function initFileInput(ctrlName) {
        var control = $('#' + ctrlName);
        control.fileinput({
            uploadUrl: "${APP_PATH}/uploadFile/importPublicLesson",//上传的地址
            uploadAsync: true,              //异步上传
            showCaption: true,              //是否显示标题
            showUpload: true,               //是否显示上传按钮
            showRemove: true,               //是否显示移除按钮
            showPreview : false,             //是否显示预览按钮
            browseClass: "btn btn-primary", //按钮样式
            dropZoneEnabled: false,         //是否显示拖拽区域
            allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀
            maxFileCount: 1                        //最大上传文件数限制
        });
    }

    //下载导入模板
    $("#file").on("fileuploaded", function (event, data, previewId, index) {
        //event当前事件，data则是数据，包括上传后服务器返回的数据+ 自身的一些数据
        //data.response才为服务器返回的数据，如果是data.filenames则为当前要上传的文件名(不包括上传的)
        //previewId 预览的下标，从0开始计算，注意并不是数字，而是一串字符串 + _index
        //index 下标，预览下标与index不一致，因为当你导入sortable.js可以排序就可能会不一致
        layer.msg('课程信息导入成功',{time:3000,icon:1,shift:6});
        $("#import_modal").modal("hide");


    });

</script>
</body>
</html>
