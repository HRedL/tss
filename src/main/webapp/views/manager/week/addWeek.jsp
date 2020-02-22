<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%
        String path=request.getContextPath();
        request.setAttribute("APP_PATH",path);
    %>

    <link rel="stylesheet" href="<c:url value="/tools/bootstrap/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/font-awesome.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/tools/css/main.css"/>">
    <link rel="stylesheet" href="<c:url value="/tools/css/bootstrap-datetimepicker.min.css"/>">
    <title>考试周管理</title>
</head>
<body>
<jsp:include page="../common/managerTitle.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="<c:url value="/views/manager/week/manageWeek.jsp"/>">考试周管理</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <label >学期</label>
                            <input type="text" class="form-control" id="term"  placeholder="请输入学期">
                        </div>
                        <div class="form-group">
                            <label>日期</label>
                            <input type="text" placeholder="日期" id="date" />
                        </div>
                        <button type="button" id="save_btn" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 提交</button>
                    </form>
                </div>
            </div>

        </div>
    </div>
</div>

<script src="<c:url value="/tools/jquery/jquery-2.1.1.min.js"/>"></script>
<script src="<c:url value="/tools/bootstrap/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/tools/script/docs.min.js"/>"></script>
<script src="<c:url value="/tools/script/bootstrap-datetimepicker.min.js"/>"></script>
<script src="<c:url value="/tools/script/locales/bootstrap-datetimepicker.zh-CN.js"/>"></script>

<script type="text/javascript">

    $(function() {
        $.fn.datetimepicker.dates['zh-CN'] = {
            days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
            daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
            daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
            months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            today: "今日",
            suffix: [],
            meridiem: ["上午", "下午"],
            weekStart: 1
        };

    });
    $('#date').datetimepicker({
        language: "zh-CN",//使用中文
        format: 'yyyy-mm-dd',//日期格式化
        todayHighlight: true,//当前日期高亮
        minView: 2,//最小，，，，设置为day
        maxView: 4,//最大，，，设置为decade
        autoclose: true//选择一个日期之后自动关闭

    });


    $("#save_btn").click(function () {
        //获取对应的数据的值
        var term=$("#term").val();
        var date =$("#date").val();

        var jsonData={
            "term":term,
            "date":date
        };
        updateWeek(jsonData);
    });

    function updateWeek(jsonData) {
        $.ajax({
            url:"${APP_PATH}/week/add",
            type:"post",
            data:jsonData,
            success:function(result) {
                if(result.success==false){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                    return;
                }
                window.location.href="${APP_PATH}/views/manager/week/manageWeek.jsp";
            }
        });
    }

</script>
</body>
</html>
