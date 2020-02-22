<%--
  Created by IntelliJ IDEA.
  User: 叶之于秋
  Date: 2019/5/7
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.eas.modules.exam.entity.Exam" %>
<%@ page import="org.springframework.ui.ModelMap" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <title>EAS-监考信息查看</title>
</head>
<body>

    <jsp:include page="common/teacherTitle.jsp" />

    <div class="container-fluid">
        <div class="row">

            <jsp:include page="common/teacherMenu.jsp"/>

            <div style="margin-top: 60px; margin-right:30px; float: right; width: 80%">
                <table class="table table-hover">
                    <h3 style="margin-left: 550px; font-family: KaiTi">监考信息</h3>
                    <thead>
                    <tr style="font-family: KaiTi; font-size: medium">
                        <th>课程号</th><th>监考科目</th>
                        <th>监考考场</th><th>监考日期</th><th>监考时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${exams}" var="e" varStatus="s">
                        <tr>
                            <td>${e.lesson.lnum}</td>
                            <td>${e.lesson.subject}</td>
                            <td>${e.availableRoom.room.building.campus}${e.availableRoom.room.building.bnum}${e.availableRoom.room.rnum}</td>
                            <td>${e.availableRoom.week.date}</td>
                            <td>${e.availableRoom.period.starttime} - ${e.availableRoom.period.endtime}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/tools/jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/tools/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/tools/script/docs.min.js"></script>
    <script type="text/javascript" />

</body>
</html>
