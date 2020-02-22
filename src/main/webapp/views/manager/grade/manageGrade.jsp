<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/12/28
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh-CN"><head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <%
        String path=request.getContextPath();
        request.setAttribute("APP_PATH",path);
    %>

    <link rel="stylesheet" href="${APP_PATH}/tools/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/tools/css/font-awesome.min.css ">
    <link rel="stylesheet" href="${APP_PATH}/tools/css/main.css">
    <title>EAS-班级管理</title>

</head>
<body>
<jsp:include page="../common/managerTitle.jsp"/>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../common/managerMenu.jsp"/>

            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>班级管理</h3>
                    </div>
                    <div class="panel-body">
                        <form class="form-inline" role="form" style="float:left;margin: -10px" action="${APP_PATH}/grade/getGradeByCondition" method="post" id="query_form">
                            <div class="form-group has-feedback">
                                <div class="input-group">
                                    <div class="input-group-addon">查询条件</div>
                                    <input class="form-control has-success" type="text" id="query_text_input" placeholder="请输入查询条件">
                                </div>
                            </div>
                            <button type="button" class="btn btn-warning" id="query_text_btn"><i class="glyphicon glyphicon-search"></i> 查询</button>
                        </form>
                        <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;margin-top: -10px" id="del_all_btn"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                        <button type="button" class="btn btn-primary" style="float:right;margin-top: -10px" id="add_btn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <br>
                        <hr style="clear:both;margin-top: 10px">
                        <div class="table-responsive">
                            <form id="user_form"style="margin-top: -10px">
                                <table class="table table-hover">
                                    <thead>
                                    <tr >
                                        <th width="30"><input type="checkbox" id="check_all"style="margin-top: -20px"/></th>
                                        <th>班级名</th>
                                        <th>学院</th>
                                        <th>系</th>
                                        <th>班级总人数</th>
                                        <th width="160">操作</th>
                                    </tr>
                                    </thead>
                                    <!--数据信息-->
                                    <tbody id="mainData">
                                    </tbody>

                                    <!--页码信息-->
                                    <tfoot>
                                    <tr >
                                        <td colspan="6" align="center">
                                            <!--页码信息-->
                                            <ul id="pageInf" class="pagination">
                                            </ul>
                                        </td>
                                    </tr>
                                    </tfoot>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
    </div>
</div>
<script src="${APP_PATH}/tools/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/tools/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/tools/script/docs.min.js"></script>
<script src="${APP_PATH}/tools/layer/layer.js"></script>
<script type="text/javascript">

    var likeflag=false;

    $(function () {
        pageQuery(1);
    });

    //条件查询
    $("#query_text_btn").click(function () {
        //获取查询条件
        var queryText=$("#query_text_input").val();
        //如果有查询条件就下面就进入有查询条件的分页查询
        if(queryText==""){
            likeflag=false;
        }else {
            likeflag=true;
        }
        pageQuery(1);
    });

    //分页查询
    function pageQuery(pageNumber){

        var jsonData={
            "pageNumber":pageNumber,
            "pageSize":10
        };
        //如果要模糊查询就传输数据
        if(likeflag==true){
            jsonData.queryText=$("#query_text_input").val();
        }

        $.ajax({
            url:"${APP_PATH}/grade/list",
            type:"get",
            data:jsonData,
            beforeSend:function () {
                loadingIndex=layer.msg('处理中',{icon:16});
            },
            success:function(result) {
                layer.close(loadingIndex);

                if(result.success==false){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                    return;
                }
                var tbodyInf="";
                var pageInf="";
                var pageInfo=result.body.pageInfo;
                var grades=pageInfo.list;
                var pageSize=0;
                $.each(grades,function (i,grade) {
                    tbodyInf+='<tr>';
                    tbodyInf+='<td><input type="checkbox" class="check_item" value="'+grade.id+'"/></td>';
                    tbodyInf+='<td>'+grade.gname+'</td>';
                    tbodyInf+='<td>'+grade.academy+'</td>';
                    tbodyInf+='<td>'+grade.dept+'</td>';
                    tbodyInf+='<td>'+grade.gtotalnum+'</td>';
                    tbodyInf+='<td>';
                    tbodyInf+='<button type="button" idClass= "'+grade.id+'" class="btn btn-warning btn-xs update_btn"><i class=" glyphicon glyphicon-pencil"></i>修改</button>';
                    tbodyInf+='<button type="button" idClass= "'+grade.id+'" class="btn btn-danger btn-xs del_btn"><i class=" glyphicon glyphicon-remove"></i>删除</button>';
                    tbodyInf+='</td>';
                    tbodyInf+='</tr>';
                    pageSize++;
                    //中止循环
                    if(pageSize==pageInfo.pageSize){
                        return false;
                    }
                });

                pageInf+='<li><a href="#" onclick="pageQuery(1)">首页</a></li>';
                if(pageInfo.hasPreviousPage){
                    pageInf+='<li><a href="#" onclick="pageQuery('+(pageNumber-1)+')">上一页</a></li>';
                }

                $.each(pageInfo.navigatepageNums,function (index,item) {
                    if(item==pageNumber){
                        pageInf+='<li class="active"><a href="#">'+item+'</a></li>';
                    }else {
                        pageInf+='<li><a href="#" onclick="pageQuery('+item+')">'+item+'</a></li>';
                    }
                });

                if(pageInfo.hasNextPage){
                    pageInf+='<li><a href="#" onclick="pageQuery('+(pageNumber+1)+')">下一页</a></li>';
                }
                pageInf+='<li><a href="#" onclick="pageQuery('+pageInfo.pages+')">末页</a></li>';
                $("#mainData").html(tbodyInf);
                $("#pageInf").html(pageInf);
            }
        });
    }

    //所有单选选中，多选即选中
    $(document).on("click",".check_item",function (){
        var number=$(".check_item:checked").length;
        if(number==$(".check_item").length){
            $("#check_all").prop("checked",true);
        }else{
            $("#check_all").prop("checked",false);
        }
    });

    //给全选多选框添加点击事件
    $("#check_all").click(function(){
        var check=$(this).prop("checked");
        $(".check_item").prop("checked",check);
    });

    $("#add_btn").click(function(){
        window.location.href="${APP_PATH}/views/manager/grade/addGrade.jsp";
    });

    $(document).on("click",".update_btn",function (){
        var id=$(this).attr("idClass");
        window.location.href="${APP_PATH}/views/manager/grade/updateGrade.jsp?id="+id;
    });


    $(document).on("click",".del_btn",function (){
        var id=$(this).attr("idClass");
        layer.confirm('确认要删除该条数据吗?', function(index){
            $.ajax({
                url:"${APP_PATH}/grade/delete",
                type:"post",
                data:{
                    id:id
                },
                beforeSend:function () {
                    loadingIndex=layer.msg('处理中',{icon:16});
                },
                success:function(result) {
                    //只要请求发送成功，就取消加载的效果
                    layer.close(loadingIndex);
                    layer.close(index);

                    if(result.success==true){
                        //
                        layer.msg(result.msg, {time:5000,icon:1,shift:6});
                        window.location.reload();
                    }else{
                        layer.msg(result.msg, {time:5000,icon:2,shift:6});
                    }
                }

            })
        });
    });



    $("#del_all_btn").click(function () {

        var checked_item=$('input[class=check_item]:checked');

        var item=new Array();
        var number= checked_item.length;
        $.each(checked_item,function(index,value){
            item.push($(value).attr("value"));
        });

        if(number == 0){
            layer.msg('请选择删除的课程',{time:3000,icon:1,shift:6});
        }else{
            layer.confirm("删除已选择的课程信息,是否继续",{icon:3,title:"提示"},function (cindex) {
                $.ajax({
                    url:"${APP_PATH}/grade/deleteGrades",
                    type:"post",
                    traditional: true,
                    data:{
                        id:item
                    },
                    success:function (result) {
                        if(result.success){
                            layer.close(cindex);
                            layer.msg('课程信息删除成功',{time:3000,icon:1,shift:6});
                            window.location.reload();
                        }else{
                            layer.msg('课程信息删除失败',{time:3000,icon:2,shift:6});
                        }
                    }
                });
            });
        }
    });

</script>
</body>
</html>
