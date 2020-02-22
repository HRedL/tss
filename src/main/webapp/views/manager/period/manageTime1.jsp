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
	<title>EAS-时间管理</title>
</head>
<body>
<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
	<div class="row">
		<jsp:include page="../common/managerMenu.jsp"/>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>时间管理</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" style="float:left;">
						<div class="form-group has-feedback">
							<div class="input-group">
								<div class="input-group-addon">查询条件</div>
								<input class="form-control has-success" type="text" id="query_user_input" placeholder="请输入查询条件">
							</div>
						</div>
						<button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
					</form>
					<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id=""><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button type="button" class="btn btn-primary" style="float:right;" id="add_time_btn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
					<br>
					<hr style="clear:both;">
					<div class="table-responsive">
						<form id="user_form">
							<table class="table table-hover">
								<thead>
								<tr >
									<th width="30"><input type="checkbox"/></th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th width="160">操作</th>
								</tr>
								</thead>
								<!--数据信息-->
								<tbody id="userData">
								<c:forEach items="${pageInfo.list}" var="period">
									<tr>
										<td><input type="checkbox"/></td>
										<td>${period.starttime}</td>
										<td>${period.endtime}</td>
										<td>
											<button type="button" class="btn btn-info btn-xs"><i class="glyphicon glyphicon-search"></i>详细</button>
											<button type="button" class="btn btn-warning btn-xs"><i class="glyphicon glyphicon-pencil"></i>修改</button>
											<button type="button" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i>删除</button>
										</td>
									</tr>
								</c:forEach>
								</tbody>

								<!--页码信息-->
								<tfoot>
								<tr >
									<td colspan="6" align="center">
										<!--页码信息-->
										<ul id="pageInf" class="pagination">
											<li><a href="<c:url value="/period/list?pageNumber=1"/>">首页</a></li>
											<c:if test="${pageInfo.hasPreviousPage}">
												<li>
													<a href="<c:url value="/period/list?pageNumber=${pageInfo.pageNum-1}"/>" aria-label="Previous">
														<span aria-hidden="true">&laquo;</span>
													</a>
												</li>
											</c:if>
											<c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
												<c:if test="${pageNum==pageInfo.pageNum}">
													<li class="active"><a href="<c:url value="/period/list?pageNumber=${pageNum}"/>">${pageNum}</a></li>
												</c:if>
												<c:if test="${pageNum!=pageInfo.pageNum}">
													<li><a href="<c:url value="/period/list?pageNumber=${pageNum}"/>">${pageNum}</a></li>
												</c:if>
											</c:forEach>
											<c:if test="${pageInfo.hasNextPage}">
												<li>
													<a href="<c:url value="/period/list?pageNumber=${pageInfo.pageNum+1}"/>" aria-label="Next">
														<span aria-hidden="true">&raquo;</span>
													</a>
												</li>
											</c:if>
											<li><a href="<c:url value="/period/list?pageNumber=${pageInfo.pages}"/>">末页</a></li>
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
<script type="text/javascript">

    $("#add_time_btn").click(function(){
        window.location.href="<c:url value="/period/toAddPage"/>";
    });


</script>
</body>
</html>
