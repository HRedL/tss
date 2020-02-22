<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>>
<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/user/list"/>"><i class="glyphicon glyphicon glyphicon-tasks"></i>用户管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/sysmanager/common/sendMsg.jsp"/>"><i class="glyphicon glyphicon-th-large"></i>反馈消息管理</a></span>
            </li>
        </ul>
    </div>
</div>


