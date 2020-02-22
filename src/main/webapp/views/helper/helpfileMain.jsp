<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">

<head>
    <%
        String path=request.getContextPath();
        request.setAttribute("APP_PATH",path);
    %>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <link rel="stylesheet" href="${APP_PATH}/tools/file_input/css/fileinput.min.css"/>
    <title>帮助</title>

</head>
<body>
<div>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="common/helpMenu.jsp"/>
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
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" >
    <div><h1>关于EAS</h1></div>
    <div><h3>介绍</h3></div>
    <section ui-type="index-banner" class="index-banner ">
        <div class="container"> <div class="group">
            <h5>EAS考试排考系统，采用智能化的算法实现排考功能，致力于用更简便的方式对考试相关事务进行组织安排，满足教师和学生查询相关考次的需求，减轻排考老师负担。</h5>
        </div>
        </div>
    </section>

    <section ui-type="card-links" class="card-links ">
        <div class="container">
            <div><h4>使用指南</h4></div>
            <div class="card-content">
                <div class="block-wraper-col3">
                    <article data-ui-index="0" style="height: 152px;">
                        <div class="head" id="sysmanager">
                            <span class="title" style="color: #006dcc">系统管理员使用指南</span>
                        </div>
                        <div class="detail-content">
                            <ul>
                                <br><li>管理用户：系统管理员可以查看并管理所有用户信息</li>
                                <br><li>反馈消息管理：系统管理员查看并回复其他用户的消息反馈</li>
                            </ul>
                        </div>
                        <div class="head" id="manager">
                            <span class="title"  style="color: #006dcc">监考管理员使用指南</span>
                        </div>
                        <div class="detail-content">
                            <ol>
                                <br><li>监考管理员检查并管理教室、时间、考试周数据，检查无误后进入可用教室生成界面，可用教室是根据教室、时间、考试周生成的数据，<br>可以分配给课程，使该课程在该可用教室内考试。系统已编写好算法，用户只需要点击左上角一键生成、一键删除按钮，可以生成相应可用<br>教室，并在可用教室查看界面查看、管理可用教室</li>
                                <br><li>监考管理员需要导入的数据：
                                    <ol type="a">
                                        <li>进入课程管理菜单，点击右上角 “Excel导入”按钮按格式从本地导入xls、xlsx文件。导入后刷新当前页查看是否导入成功，<br>若数据未更新则为格式问题，修改Excel文件后重新导入。</li><br>
                                        <ul><li list-style-type:none>Excel具体格式如下：</li><br>
                                        <li list-style-type:none>数据需从第二行开始，各列内容如图所示（课程号为文本类型，选课人数为文本类型）</li><br>
                                        <li list-style-type:none><img src="${pageContext.request.contextPath}/views/helper/help1.jpg"></li></ul><br>
                                        <li>进入公共课管理菜单，点击右上角“Excel导入”按钮，使用步骤与课程导入相同，但格式不同，具体格式如下：</li><br>
                                        <ul><li>第一列必须为空，其他列内容如图所示：第二列为课课程号（文本类型）、第三列为课程名、第四列为考试人数（文本类型）<br>第五列为考试日期、第六列为考试时间、第七列为考试教室</li><br>
                                        <li>考试日期、时间请按如图所示格式，注意时间8:00不能写成08：00（冒号需要是英文冒号）</li><br>
                                        <li list-style-type:none><img src="${pageContext.request.contextPath}/views/helper/help2.jpg"></li></ul><br>
                                        <li>导入公共课数据后，在公共课管理页面点击上方插入考次表按钮，再点击更新可用教室按钮</li>
                                    </ol></li>
                                <br><li>监考管理员检查并管理教室、时间、考试周数据，检查无误后进入可用教室生成界面，可用教室是根据教室、时间、考试周生成的数据，<br>可以分配给课程，使该课程在该可用教室内考试。系统已编写好算法，用户只需要点击左上角一键生成、一键删除按钮，可以生成相应可用<br>教室，并在可用教室查看界面查看、管理可用教室</li>
                                <br><li>监考管理员检查并管理教师、班级、学生、课程、可用教室等数据，检查无误后进入考次管理界面，系统已编写好相应的考次排考算法，<br>用户只需要点击一键生成按钮并刷新界面查看生成考次、进行微调即可</li>
                                <br><li>用户使用本系统过程中遇到任何问题都可以使用消息反馈菜单内反馈功能向系统管理员反馈并查询回复。通过教师管理、学生管理功能得<br>知其他用户的账号后（学生是学号、教师是教工号）使用该账号作为接受人，可以发消息与其他用户进行站内通信</li>
                            </ol>
                        </div>
                        <div class="head" id="student"><span class="title"  style="color: #006dcc">学生使用指南</span>
                        </div>
                        <div class="detail-content">
                            <ul>
                                <br><li>查看考试信息：学生用户可以登录该系统查看自己的考试科目、时间、地点等信息</li>
                                <br><li>个人信息：学生用户可以在系统中查看个人信息、修改登录密码、上传头像等</li>
                                <br><li>消息反馈：学生用户得知其他用户的账号后（学生是学号、教师是教工号）使用该账号作为接受人，可以发消息与其他用户进行站内通信；<br>使用反馈功能可以向监考管理员（即教务工作人员）反馈考次出现的差错及疑问，向系统管理员反馈本系统存在的问题并查看相应回复</li>
                            </ul>
                        </div>
                        <div class="head" id="teacher">
                            <span class="title"  style="color: #006dcc">教师使用指南</span>
                        </div>
                        <div class="detail-content">
                            <ul>
                                <br><li>查看监考信息：教师用户可以登录该系统查看自己监考的考试科目、时间、地点等信息</li>
                                <br><li>个人信息：教师用户可以在系统中查看个人信息、修改登录密码、上传头像等</li>
                                <br><li>消息反馈：教师用户得知其他用户的账号后（学生是学号、教师是教工号）使用该账号作为接受人，可以发消息与其他用户进行站内通信；,<br>使用反馈功能可以向监考管理员（即教务工作人员）反馈监考安排出现的差错及疑问，向系统管理员反馈本系统存在的问题并查看相应回复</li>
                            </ul>
                        </div>
                        <div class="head" id="other">
                            <span class="title"  style="color: #006dcc">其他问题</span>
                        </div>
                        <div class="detail-content">
                            <ul>
                                <br><li>账号登录：青岛大学学生使用学号作为账号密码登录使用本系统、青岛大学教工使用教工号做为账号密码登录使用本系统</li>
                            </ul>
                        </div>
                    </article>
                </div>
            </div>
        </div>
</section>
</div>
</body>
</html>
