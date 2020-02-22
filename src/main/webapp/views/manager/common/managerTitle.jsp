
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path=request.getContextPath();
    request.setAttribute("PATH",path);
%>
<!-- 模态框（Modal） -->
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
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation" style="background: #4682B4;">
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
