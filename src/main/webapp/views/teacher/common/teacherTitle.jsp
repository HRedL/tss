<%--
  Created by IntelliJ IDEA.
  User: 叶之于秋
  Date: 2019/5/7
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="com.eas.modules.user.entity.User" %>

<%
    String PATH=request.getContextPath();
    request.setAttribute("PATH",PATH);
    User user = (User)session.getAttribute("user");
    String name = user.getName();
    String logname = user.getLogname();
    String type = user.getType();
    String pic = user.getPic();
%>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation"style="background: #4682B4;">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><p class="navbar-brand"  style="font-size:32px;color:#F0FFFF">EAS监考排考系统</p></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-user"></i> <%=name%> <span id="logname">${sessionScope.user.logname}</span><span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${pageContext.request.contextPath}/teacher/show_personage">
                                    <i class="glyphicon glyphicon-cog"></i>
                                    个人设置
                                </a>
                            </li>
                            <li>
                                <a href="<c:url value="/views/teacher/common/sendMsg.jsp"/>">
                                    <i class="glyphicon glyphicon-comment"></i>
                                    消息
                                </a>
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
