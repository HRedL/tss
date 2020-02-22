<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/8/18
  Time: 18:50
  To change this template use File | Settings | File Templates.
  When I wrote this, only God and I understood what I was doing
  Now, God only knows 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
  <head>
    <title>TSS</title>
  </head>
  <body>
  lilinzhen test
  <p>实验一：</p>
    <a href="huahonglei/huahonglei090100">化红磊</a>
	<a href="maqianyun/maqianyun0901">马倩云</a>
    <a href="liuliping/liuliping090100">刘莉萍</a>
    <a href="wanghuiyao/wanghuiyao0901">王汇尧</a>
    <a href="huwenqian/huwenqian100100">胡文倩</a>
  <br><br>

  <p>实验三：</p>

  <a href="lesson/list">查看课程</a><br>

  <a href="exam/list">查看考次</a><br>

  <a href="dict/list">查看字典表</a><br>

  <a href="user/list">查看用户表</a><br>

  <a href="teacher/list">查看教师</a><br>

  <a href="student/list">查看学生</a><br>

  <a href="availableroom/list">查看可用教室</a><br>

  <a href="period/list">查看考试的时间段</a><br>
  
  <a href="week/list">查看考试周信息</a><br>
  
  <a href="grade/list">查看班级信息</a><br>

  <a href="building/list">查看教学楼</a><br>

  <a href="room/list">查看教室</a><br>


  <a href="user/login">脑壳子疼</a>



  <a href="/Spring-Mybatis-Druid/user/export">导出</a> <br/>

  <form action="/Spring-Mybatis-Druid/user/import" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit" value="导入Excel">
  </form>

  </body>
</html>
