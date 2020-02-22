<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>>
<div class="col-sm-3 col-md-2 sidebar" style="top: 0px;">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <li class="list-group-item tree-closed">
                <span><a href="javascript:;" onclick="document.getElementById('sysmanager').scrollIntoView(true);"><i class="glyphicon glyphicon glyphicon-tasks"></i>系统管理员使用指南</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="javascript:;" onclick="document.getElementById('manager').scrollIntoView(true);"><i class="glyphicon glyphicon-th-large"></i>监考管理员使用指南</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="javascript:;" onclick="document.getElementById('student').scrollIntoView(true);"><i class="glyphicon glyphicon glyphicon-tasks"></i>学生使用指南</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="javascript:;" onclick="document.getElementById('teacher').scrollIntoView(true);"><i class="glyphicon glyphicon glyphicon-tasks"></i>教师使用指南</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="javascript:;" onclick="document.getElementById('other').scrollIntoView(true);"><i class="glyphicon glyphicon glyphicon-tasks"></i>其他问题</a></span>
            </li>
        </ul>
    </div>
</div>