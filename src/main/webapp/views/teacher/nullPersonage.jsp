<%--
  Created by IntelliJ IDEA.
  User: 叶之于秋
  Date: 2019/5/9
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
</head>
<body>
<jsp:include page="common/teacherTitle.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="common/teacherMenu.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h3 style="margin-top: 50px; color: #AA0000">${errorPersonage}</h3>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/tools/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/tools/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/tools/script/docs.min.js"></script>
<script type="text/javascript" />
</body>
</html>
