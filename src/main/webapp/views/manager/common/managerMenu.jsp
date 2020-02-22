<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>>
<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <li class="list-group-item tree-closed" >
                <a href="<c:url value="/views/manager/lesson/manageLesson.jsp"/>"><i class="glyphicon glyphicon-th"></i>课程管理</a>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/publicLesson/managePublicLesson.jsp"/>"><i class="glyphicon glyphicon-book"></i>公共课管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/teacher/manageTeacher.jsp"/>"><i class="glyphicon glyphicon glyphicon-user"></i>教师管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/period/manageTime.jsp"/>"><i class="glyphicon glyphicon-dashboard"></i>时间管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/week/manageWeek.jsp"/>"><i class="glyphicon glyphicon-calendar"></i>考试周管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/availableRoom/manageAvailableRoom.jsp"/>"><i class="glyphicon glyphicon-search"></i>可用教室生成</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/availableRoom/manageAvailableRoom1.jsp"/>"><i class="glyphicon glyphicon-search"></i>可用教室管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/exam/manageExam.jsp"/>"><i class="glyphicon glyphicon-edit"></i>考次管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/grade/manageGrade.jsp"/>"><i class="glyphicon glyphicon-align-left"></i>班级管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/student/manageStudent.jsp"/>"><i class="glyphicon glyphicon-pencil"></i>学生管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/room/manageRoom.jsp"/>"><i class="glyphicon glyphicon-home"></i>教室管理</a></span>
            </li>
            <li class="list-group-item tree-closed">
                <span><a href="<c:url value="/views/manager/common/sendMsg.jsp"/>"><i class="glyphicon glyphicon-th-large"></i>消息反馈</a></span>
            </li>
        </ul>
    </div>
</div>


