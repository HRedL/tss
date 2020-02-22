<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="GB18030">
  <head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	  <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
	  <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
	  <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
      <link rel="stylesheet" href="${PATH}/tools/file_input/css/fileinput.min.css"/>
      <title>EAS-用户管理</title>
  </head>
  <body>
  <%
      String PATH=request.getContextPath();
      request.setAttribute("PATH",PATH);
  %>
  <jsp:include page="../common/sysManagerTitle.jsp"/>

    <div class="container-fluid">
	<div class="row">
		<jsp:include page="../common/sysManagerMenu.jsp"/>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>用户管理</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" id="query_form" style="float:left;" action="<c:url value="/user/getUserByCondition"/>" method="post" >
						<div class="form-group has-feedback">
							<div class="input-group">
								<div class="input-group-addon">查询条件</div>
								<input class="form-control has-success" type="text"  name="queryText"  placeholder="请输入查询条件">
							</div>
						</div>
						<button type="button" class="btn btn-warning" id="query_user_btn"><i class="glyphicon glyphicon-search"></i> 查询</button>
					</form>
					<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="delete_user_btn"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button type="button" class="btn btn-primary" style="float:right;" id="add_user_btn" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
					<br>
					<hr style="clear:both;">
					<div class="table-responsive">
						<form id="user_form">
							<table class="table table-hover">
								<thead>
								<tr >
									<th width="30"><input type="checkbox" id="check_all_user"/></th>
									<th width="25%">用户名</th>
									<th width="25%">姓名</th>
									<th width="20%">用户类别</th>
									<th width="160">操作</th>
								</tr>
								</thead>
								<!--数据信息-->
								<tbody id="userData">
								<%--@elvariable id="pageInfo" type="com.github.pagehelper.PageInfo"--%>
								<c:forEach items="${pageInfo.list}" var="user">
									<tr>
                                        <th width="30"><input type="checkbox"  class="check_item" value="${user.id}"/></th>
										<td>${user.logname}</td>
										<td>${user.name}</td>
										<td>${user.type}</td>
                                        <td>
                                            <button type="button" class="btn btn-warning btn-xs update_btn" idClass="${user.id}"><i class="glyphicon glyphicon-pencil"></i>修改</button>
                                            <button type="button" class="btn btn-danger btn-xs delete_btn" idClass="${user.id}"><i class="glyphicon glyphicon-remove"></i>删除</button>
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
											<li><a href="<c:url value="/user/list?pn=1"/>">首页</a></li>
											<c:if test="${pageInfo.hasPreviousPage}">
												<li>
													<a href="<c:url value="/user/list?pn=${pageInfo.pageNum-1}"/>" aria-label="Previous">
														<span aria-hidden="true">&laquo;</span>
													</a>
												</li>
											</c:if>
											<c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
												<c:if test="${pageNum==pageInfo.pageNum}">
													<li class="active"><a href="<c:url value="/user/list?pn=${pageNum}"/>">${pageNum}</a></li>
												</c:if>
												<c:if test="${pageNum!=pageInfo.pageNum}">
													<li><a href="<c:url value="/user/list?pn=${pageNum}"/>">${pageNum}</a></li>
												</c:if>
											</c:forEach>
											<c:if test="${pageInfo.hasNextPage}">
												<li>
													<a href="<c:url value="/user/list?pn=${pageInfo.pageNum+1}"/>" aria-label="Next">
														<span aria-hidden="true">&raquo;</span>
													</a>
												</li>
											</c:if>
											<li><a href="<c:url value="/user/list?pn=${pageInfo.pages}"/>">末页</a></li>
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
  <script type="text/javascript">
      //给全选多选框添加点击事件
      $("#check_all_user").click(function(){
          var check=$(this).prop("checked");
          $(".check_item").prop("checked",check);
      });
      //所有单选选中，多选即选中
      $(document).on("click",".check_item",function (){
          var number=$(".check_item:checked").length;
          if(number==$(".check_item").length){
              $("#check_all_user").prop("checked",true);
          }else{
              $("#check_all_user").prop("checked",false);
          }
      });
      //跳转到addUser页面
      $("#add_user_btn").click(function(){
          window.location.href="<c:url value="/user/toAddPage"/>";

      });
	//查询
      $("#query_user_btn").click(function () {
          $("#query_form").submit();
      });
	//更新
      $(".update_btn").click(function () {
          var id=$(this).attr("idClass");
          window.location.href="${PATH}/user/toUpdatePage?id="+id;
      })
	  //删除单条数据
      $(document).on("click",".delete_btn",function (){
          var id=$(this).attr("idClass");
          layer.confirm('确认要删除该条数据吗?', function(index){
              $.ajax({
                  url:"${PATH}/user/delete/"+id,
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
		//复选框选中数据的删除
      $("#delete_user_btn").click(function () {
          var checked_item=$('input[class=check_item]:checked');
          var item=new Array();
          var number= checked_item.length;
          $.each(checked_item,function(index,value){
              item.push($(value).attr("value"));
          });

          if(number == 0){
              layer.msg('请选择删除的用户',{time:3000,icon:1,shift:6});
          }else{
              layer.confirm("删除已选择的用户信息,是否继续",{icon:3,title:"提示"},function (cindex) {
                  $.ajax({
                      url:"${PATH}/user/deleteUsers",
                      type:"post",
                      traditional: true,
                      data:{
                          id:item
                      },
                      success:function (result) {
                          layer.close(cindex);
                          if(result.success){
                              layer.msg('用户信息删除成功',{time:3000,icon:1,shift:6});
                              window.location.reload();
                          }else{
                              layer.msg('用户信息删除失败',{time:3000,icon:2,shift:6});
                          }
                      }
                  });
              });
          }
      });
  </script>
  </body>
</html>
