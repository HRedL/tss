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
	<link rel="stylesheet" href="${APP_PATH}/tools/file_input/css/fileinput.min.css"/>
	<title>EAS-课程管理</title>

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
						<label class="col-sm-4 control-label">课程号</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_lesson_lnum"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">课程名</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_lesson_name"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">课程类型</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_lesson_type"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label" >课程人数</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_lesson_ltotaonum"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">授课教师-教师号</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_teacher_tnum"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">授课教师-教师名</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_teacher_tname"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">上课班级</label>
						<div class="col-sm-8">
							<p class="form-control-static" id="detail_groups"></p>
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
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>课程管理</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" style="float:left;margin: -10px">
						<div class="form-group has-feedback">
							<div class="input-group">
								<div class="input-group-addon">查询条件</div>
								<input class="form-control has-success" type="text" id="query_text_input" placeholder="请输入查询条件">
							</div>
						</div>
						<button type="button" class="btn btn-warning" id="query_text_btn"><i class="glyphicon glyphicon-search"></i> 查询</button>
					</form>
					<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;margin-top: -10px" id="import_lesson"><i class=" glyphicon glyphicon-remove"></i> Excel导入</button>
					<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;margin-top: -10px" id="del_all_btn"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button type="button" class="btn btn-primary" style="float:right;margin-top: -10px" id="add_btn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
					<br>
					<hr style="clear:both;margin-top: 10px">
					<div class="table-responsive">
						<form id="user_form" style="margin-top: -10px">
							<table class="table table-hover">
								<thead>
								<tr >
									<th width="30"><input type="checkbox" id="check_all" style="margin-top: -20px"/></th>
									<th width="50">课程号</th>
									<th>科目名</th>
									<th>课程人数</th>
									<th>课程类型</th>
									<th width="160">操作</th>
								</tr>
								</thead>
								<!--数据信息-->
								<tbody id="lessonData">
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
<script src="<c:url value="/tools/layer/layer.js"/>"></script>
<script src="${APP_PATH}/tools/file_input/js/fileinput.min.js"></script>
<script src="${APP_PATH}/tools/file_input/js/locales/zh.js"></script>

<script type="text/javascript">

	//设置初始没有查询条件
	var likeflag=false;
    $(function () {
        pageQuery(1);
    });

    //给条件查询按钮添加点击事件
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

    //给全选多选框添加点击事件
    $("#check_all").click(function(){
        var check=$(this).prop("checked");
        $(".check_item").prop("checked",check);
    });
	//所有单选选中，多选即选中
    $(document).on("click",".check_item",function (){
        var number=$(".check_item:checked").length;
        if(number==$(".check_item").length){
            $("#check_all").prop("checked",true);
        }else{
            $("#check_all").prop("checked",false);
        }
    });

    function pageQuery(pageNumber){

        var jsonData={
            "pageNumber":pageNumber,
            "pageSize":10
        };
        //如果要模糊查询就传输数据
        if(likeflag==true){
            jsonData.queryText=$("#query_text_input").val();
        }
        $.ajax({
            url:"${APP_PATH}/lesson/list",
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
                var lessons=pageInfo.list;
                $.each(lessons,function (i,lesson) {
                    tbodyInf+='<tr>';
                    tbodyInf+='<td><input type="checkbox" class="check_item" value="'+lesson.id+'"/></td>';
                    tbodyInf+='<td>'+lesson.lnum+'</td>';
                    tbodyInf+='<td>'+lesson.subject+'</td>';
                    tbodyInf+='<td>'+lesson.ltotalnum+'</td>';
                    tbodyInf+='<td>'+lesson.type+'</td>';
                    tbodyInf+='<td>';
                    tbodyInf+='<button type="button" idClass="'+lesson.id+'" class="btn btn-info btn-xs detail_btn"><i class=" glyphicon glyphicon-check"></i> 详细</button>';
                    tbodyInf+='<button type="button" idClass= "'+lesson.id+'" class="btn btn-warning btn-xs update_btn"><i class=" glyphicon glyphicon-pencil"></i>修改</button>';
                    tbodyInf+='<button type="button" idClass= "'+lesson.id+'" class="btn btn-danger btn-xs del_btn"><i class=" glyphicon glyphicon-remove"></i>删除</button>';
                    tbodyInf+='</td>';
                    tbodyInf+='</tr>';
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
                $("#lessonData").html(tbodyInf);
                $("#pageInf").html(pageInf);
            }
        });
    }





	//跳转到addLesson页面
    $("#add_btn").click(function(){
        window.location.href="${APP_PATH}/views/manager/lesson/addLesson.jsp";
    });

    $(document).on("click",".update_btn",function (){
        var id=$(this).attr("idClass");
        window.location.href="${APP_PATH}/views/manager/lesson/updateLesson.jsp?id="+id;
    });

    $(document).on("click",".detail_btn",function (){
        var id=$(this).attr("idClass");
        $.ajax({
            url:"${APP_PATH}/lesson/detail",
            type:"get",
            data:{
                id:id
            },
            success:function(result) {
                var lesson=result.body.lesson;
                var grades=lesson.grades;
                var detail_grades="";
                $.each(grades,function (i,grade) {
					detail_grades+=grade.gname;
					detail_grades+=";";
                });
				$("#detail_lesson_lnum").html(lesson.lnum);
				$("#detail_lesson_name").html(lesson.subject);
				$("#detail_lesson_type").html(lesson.type);
				$("#detail_lesson_ltotaonum").html(lesson.ltotalnum);
				$("#detail_teacher_tnum").html(lesson.teacher.tnum);
				$("#detail_teacher_tname").html(lesson.teacher.tname);
				$("#detail_groups").html(detail_grades);
            }
        });
        $("#detail_modal").modal('show');
    });

    $(document).on("click",".del_btn",function (){
        var id=$(this).attr("idClass");
        layer.confirm('确认要删除该条数据吗?', function(index){
            $.ajax({
                url:"${APP_PATH}/lesson/delete",
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

                    if(result.success==true){
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
            layer.msg('请选择删除的课程',{period:3000,icon:1,shift:6});
        }else{
            layer.confirm("删除已选择的课程信息,是否继续",{icon:3,title:"提示"},function (cindex) {
                $.ajax({
                    url:"${APP_PATH}/lesson/deleteLessons",
                    type:"post",
                    traditional: true,
                    data:{
                        id:item
                    },
                    success:function (result) {
                        if(result.success){
                            layer.close(cindex);
                            layer.msg('课程信息删除成功',{time:3000,icon:1,shift:6});
                            window.location.reload();
                        }else{
                            layer.msg('课程信息删除失败',{time:3000,icon:2,shift:6});
                        }
                    }
                });
            });
        }
    });

    //导入文件
	$("#import_lesson").click(function () {
		$("#import_modal").modal('show');
		initFileInput("file");
    });

    function initFileInput(ctrlName) {
        var control = $('#' + ctrlName);
        control.fileinput({
            uploadUrl: "${APP_PATH}/uploadFile/importLesson",//上传的地址
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
