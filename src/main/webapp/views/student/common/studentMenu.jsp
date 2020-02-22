<%--
  Created by IntelliJ IDEA.
  User: 叶之于秋
  Date: 2019/5/7
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<div class="container-fluid" style="float:left">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <ul style="padding-left:0px;" class="list-group">
                    <li class="list-group-item tree-closed" >
                        <a href="${pageContext.request.contextPath}/student/show_stu_info"
                            style="font-size: 20px; font-family: KaiTi">
                            <i class="glyphicon glyphicon-dashboard"></i> 学生考试安排
                        </a>
                    </li>
                    <li class="list-group-item tree-closed">
                        <a href="${pageContext.request.contextPath}/student/show_personage"
                           style="font-size: 20px; font-family: KaiTi">
                            <i class="glyphicon glyphicon glyphicon-tasks"></i> 我的信息
                        </a>
                    </li>
                    <li class="list-group-item tree-closed">
                        <a href="<c:url value="/views/student/common/sendMsg.jsp"/>"
                           style="font-size: 20px; font-family: KaiTi">
                            <i class="glyphicon glyphicon-th-large"></i> 消息反馈
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
