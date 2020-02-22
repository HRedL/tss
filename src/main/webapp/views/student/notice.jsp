<%--
  Created by IntelliJ IDEA.
  User: 叶之于秋
  Date: 2019/5/9
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/Style/StudentStyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/Skins/Blue/jbox.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/Style/ks.css" rel="stylesheet" type="text/css" />

</head>
<body>
<jsp:include page="common/studentTitle.jsp" />

<div class="container-fluid">
    <div class="row">

        <jsp:include page="common/studentMenu.jsp"/>

        <div class="rightbox"
             style="margin-top: 60px; margin-right:250px; float: right; width: 60%">
            <h2 class="mbx" style="font-size: 15px">我的信息 &gt; 通知 &nbsp;&nbsp;&nbsp;</h2>
            <div class="morebt">
                <ul id="ulStudMsgHeadTab">
                    <li><a class="tab2" onclick=""
                           href="${pageContext.request.contextPath}/student/show_personage">
                        个人资料
                    </a>
                    </li>
                    <li><a class="tab2" onclick=""
                           href="${pageContext.request.contextPath}/student/show_changePassword">
                        安全管理
                    </a>
                    </li>
                    <li><a class="tab2" onclick=""
                           href="${pageContext.request.contextPath}/student/show_notice">
                        通知<span style="color:#ff4e24; padding-left:5px;" id="unreadSysMsgCount"></span>
                    </a>
                    </li>
                </ul>
            </div>



        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/tools/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/tools/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/tools/script/docs.min.js"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/jBox/i18n/jquery.jBox-zh-CN.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/Common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/tools/student_teachaer/Script/Data.js" type="text/javascript"></script>
<script type="text/javascript">


</script>
</body>
</html>
