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
      <title>EAS-监考管理员界面</title>
  </head>
  <body>
  <%
      String PATH=request.getContextPath();
      request.setAttribute("PATH",PATH);
  %>
  <!-- 个人信息模态框（Modal） -->
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title" id="myModalLabel">个人设置</h4>
              </div>
              <div class="modal-body">
                  <form id="personage">
                      <div class="form-group">
                          <label for="firstname" class="col-sm-2 control-label">我的头像</label>
                          <div class="col-sm-10">
                              <input name="pic" style="margin-bottom: 20px" type="text" class="form-control" id="pic" value="${sessionScope.user.pic}">
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="firstname" class="col-sm-2 control-label">账号</label>
                          <div class="col-sm-10">
                              <input name="logname" style="margin-bottom: 20px" type="text" class="form-control" id="firstname" value="${sessionScope.user.logname}">
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="name" class="col-sm-2 control-label">姓名</label>
                          <div class="col-sm-10">
                              <input name="name" style="margin-bottom: 20px" type="text" class="form-control" id="name" value="${sessionScope.user.name}">
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="email" class="col-sm-2 control-label">邮箱</label>
                          <div class="col-sm-10">
                              <input name="email" value="123456@qq.com" disabled="disabled"
                                     style="margin-bottom: 20px" type="text"
                                     class="form-control" id="email" >
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="phone" class="col-sm-2 control-label">手机号</label>
                          <div class="col-sm-10">
                              <input name="phone" disabled="disabled" style="margin-bottom: 20px" type="text" class="form-control" id="phone" value="01234567890">
                          </div>
                      </div>
                  </form>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                  <button type="button" class="btn btn-primary" onclick="updatePersonage()">提交更改</button>
              </div>
          </div><!-- /.modal-content -->
      </div><!-- /.modal -->
  </div>

  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation"style="background: #4682B4;">
      <div class="container-fluid">
          <div class="navbar-header">
              <div><p class="navbar-brand"  style="font-size:32px;color:#F0FFFF">EAS考试排考系统</p></div>
          </div>

  <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav navbar-right">
          <li style="padding-top:8px;">
              <div class="btn-group">
                  <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
                      <i class="glyphicon glyphicon-user"></i> ${sessionScope.user.name}<span id="logname">${sessionScope.user.logname}</span> <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" role="menu">
                      <li>
                          <a href="#" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-cog"></i>个人设置</a>

                      </li>
                      <li>
                          <a href="${PATH}/views/manager/common/sendMsg.jsp"><i class="glyphicon glyphicon-comment"></i>消息</a>

                      </li>
                      <li class="divider"></li>
                      <li><a href="${pageContext.request.contextPath}/user/login"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                  </ul>
              </div>
          </li>
          <li style="margin-left:10px;padding-top:8px;">
              <button type="button" class="btn btn-default btn-danger" onclick="window.open('${PATH}/views/helper/helpfileMain.jsp')">
                  <span class="glyphicon glyphicon-question-sign"></span> 帮助
              </button>
          </li>
      </ul>
  </div>
  </div>
  </nav>
    <div class="container-fluid">
	<div class="row">
		<jsp:include page="common/managerMenu.jsp"/>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <section ui-type="index-banner" class="index-banner ">
                <div class="container"> <div class="group">
                    <h1>考试排考系统<a class="price" href="" target="_blank"></a></h1>
                    <p></p><p>EAS考试排考系统，采用智能化的算法实现排考功能，致力于用更简便的方式对考试相关事务进行组织安排，满足教师和学生查询相关考次的需求，减轻排考老师负担。</p><p></p>
                    <div class="btn">
                        <a href="whole.jsp">点击了解系统使用步骤及文档上传格式</a>
                    </div>
                </div>
                </div>
            </section>

            <section ui-type="card-links" class="card-links ">
                <div class="container">
                    <h3>系统功能介绍</h3>
                    <div class="card-content">
                        <div class="block-wraper-col3">
                            <article data-ui-index="0" style="height: 152px;">
                                <div class="head">
                                    <i class="svg" style="background-image: url(https://portal-cms-bos.cdn.bcebos.com/fex/%70%69%63/item/622762d0f703918f1db76c6c5c3d269759eec436.jpg)"></i>
                                    <span class="title">监考管理员功能介绍</span>
                                    <a href="" target="_blank"></a>
                                </div>
                                <div class="detail-content">
                                    <ul>
                                        <li>使用课程管理菜单按格式导入课程数据、管理课程数据</li>
                                        <li>使用公共课管理菜单按格式导入公共课数据、管理公共课数据</li>
                                        <li>使用教师管理菜单查看、管理教师数据</li>
                                        <li>使用时间管理菜单选择、查看、管理考试的时间数据</li>
                                        <li>使用考试周管理彩带选择、查看、管理考试日期数据</li>
                                        <li>使用班级管理菜单查看、管理班级数据</li>
                                        <li>使用学生管理菜单查看、管理学生数据</li>
                                        <li>使用教室管理菜单查看、管理教室数据</li>
                                        <li>使用可用教室生成菜单生成可用教室</li>
                                        <li>使用可用教室管理菜单查看、管理系统生成的可用教室</li>
                                        <li>使用考次管理菜单生成、查看考次</li>
                                    </ul>
                                </div>
                            </article>
                        </div>
                    </div>
                </div>
            </section>
		</div>
	</div>

  <script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
  <script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
  <script src="<c:url value="/tools/script/docs.min.js"/>"></script>
  <script>

      function updatePersonage() {
          $.ajax({
              "url" : "/tss/user/handle_change_personage",
              "data" : $("#personage").serialize(),//表单序列化
              "type" : "POST",
              "dataType" : "json",
              "success" : function (obj) {
                  if(obj.state == 1) {
                      alert("修改成功,请重新登录！");
                      window.location.href = "/tss/user/login";
                  } else {
                      alert(obj.message);
                  }
              }
          });
      }

  </script>
  </body>
</html>
