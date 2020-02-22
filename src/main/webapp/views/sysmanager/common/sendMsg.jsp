<%@ page pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html lang="GB18030">
<head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%
        String PATH=request.getContextPath();
        request.setAttribute("APP_PATH",PATH);
    %>

    <link rel="stylesheet" href="${APP_PATH}/tools/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/tools/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/tools/css/main.css">
    <title>EAS-反馈消息管理</title>
</head>

<body>

<%--显示详细信息的模态框（别人发给我的）--%>
<div class="modal fade" tabindex="-1" role="dialog" id="update_modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">详细信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="update_feedback_period"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">发送人</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="update_feedback_questionUser"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">接收人</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="update_feedback_answerUser"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">主题</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="update_feedback_title"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">问题</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="update_feedback_question"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">回答</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" id="update_feedback_answer" placeholder="100个字以内"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="detail_modal_update_btn">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--显示详细信息的模态框(写消息)--%>
<div class="modal fade" tabindex="-1" role="dialog" id="write_modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">详细信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">接收人</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="write_feedback_answerUser" placeholder="请输入接收人">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">主题</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="write_feedback_title" placeholder="15个字以内">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">问题</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" id="write_feedback_question" placeholder="100个字以内"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="writeFeedback_btn">添加</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--显示详细信息的模态框（我发给别人的）--%>
<div class="modal fade" tabindex="-1" role="dialog" id="detail_modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">详细信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">时间</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="detail_feedback_period"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">发送人</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="detail_feedback_questionUser"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">接收人</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="detail_feedback_answerUser"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">主题</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="detail_feedback_title"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">问题</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="detail_feedback_question"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">回答</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="detail_feedback_answer"></p>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="detail_modal_del_btn">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<jsp:include page="sysManagerTitle.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <ul style="padding-left:0px;" class="list-group">
                    <li class="list-group-item tree-closed">
                        <span><a href="#" id="writeMessage"><i class="glyphicon glyphicon-pencil"></i>写消息</a></span>
                    </li>
                    <li class="list-group-item tree-closed">
                        <span><a href="#" id="getSendMessage"><i class="glyphicon glyphicon-search"></i>我发别人的</a></span>
                    </li>
                    <li class="list-group-item tree-closed">
                        <span><a href="#" id="getReplyMessage"><i class="glyphicon glyphicon-search"></i>别人发我的</a></span>
                    </li>
                    <li class="list-group-item tree-closed" >
                        <a href="${APP_PATH}/views/sysmanager/sysManagerMain.jsp"><i class="glyphicon glyphicon-share-alt"></i>返回系统管理员界面</a>
                    </li>

                </ul>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>消息</h3>
                </div>
                <div class="panel-body">

                    <div class="table-responsive">
                       <form id="Msg_form">
                           <table class="table table-hover">
                               <thead>
                               <tr >
                                    <th>日期</th>
                                    <th>标题</th>
                                    <th>联系人</th>
                                    <th>是否回复/已读</th>
                                </tr>
                                </thead>
                                <!--数据信息-->
                                <tbody id="Msg">
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


<script src="${APP_PATH}/tools/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/tools/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/tools/script/docs.min.js"></script>
<script src="${APP_PATH}/tools/layer/layer.js"></script>
<script src="${APP_PATH}/tools/select2/dist/js/select2.min.js"></script>
<script type="text/javascript">

    $("#getSendMessage").click(function(){
        getSendMessageQuery(1);
    });

    $("#getReplyMessage").click(function(){
        getReplyMessageQuery(1);
    });

    $("#writeMessage").click(function(){
        $("#write_modal").modal('show');
    });

    function getSendMessageQuery(pageNumber){
        var logname= $("#logname").html();

        $.ajax({
            url:"${APP_PATH}/feedback/list",
            type:"get",
            data:{
                pageNumber:pageNumber,
                pageSize:12,
                logname:logname
            },
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
                var feedbacks=pageInfo.list;
                var pageSize=0;
                //tbodyInf+='<div class="container-fluid">';


                $.each(feedbacks,function (i,feedback) {
                    tbodyInf+='<tr onclick="getDetailFeedback('+feedback.id+')">';
                    //tbodyInf+='<td><input type="checkbox" class="check_item" value="'+feedback.id+'"/></td>';
                    tbodyInf+='<td>'+feedback.period+'</td>';
                    tbodyInf+='<td>'+feedback.title+'</td>';
                    tbodyInf+='<td>'+feedback.answerUser.logname+'</td>';
                    if(feedback.answer==null) {
                        tbodyInf += '<td>' + "未收到回复" + '</td>';
                    }else{
                        tbodyInf += '<td>' + "收到回复" + '</td>';
                    }
                    tbodyInf+='</tr>';

                    pageSize++;
                    //中止循环
                    if(pageSize==pageInfo.pageSize){
                        return false;
                    }
                });
               // tbodyInf+='</div>';

                pageInf+='<li><a href="#" onclick="getSendMessageQuery(1)">首页</a></li>';
                if(pageInfo.hasPreviousPage){
                    pageInf+='<li><a href="#" onclick="getSendMessageQuery('+(pageNumber-1)+')">上一页</a></li>';
                }

                $.each(pageInfo.navigatepageNums,function (index,item) {
                    if(item==pageNumber){
                        pageInf+='<li class="active"><a href="#" >'+item+'</a></li>';
                    }else {
                        pageInf+='<li><a href="#" onclick="getSendMessageQuery('+item+')">'+item+'</a></li>';
                    }
                });

                if(pageInfo.hasNextPage){
                    pageInf+='<li><a href="#" onclick="getSendMessageQuery('+(pageNumber+1)+')">下一页</a></li>';
                }
                pageInf+='<li><a href="#" onclick="getSendMessageQuery('+pageInfo.pages+')">末页</a></li>';

                $("#Msg").html(tbodyInf);
                $("#pageInf").html(pageInf);


            }
        });
    }

    function getReplyMessageQuery(pageNumber){
        var logname= $("#logname").html();

        $.ajax({
            url:"${APP_PATH}/feedback/list1",
            type:"get",
            data:{
                pageNumber:pageNumber,
                pageSize:12,
                logname:logname
            },
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
                var feedbacks=pageInfo.list;
                var pageSize=0;
                //tbodyInf+='<div class="container-fluid">';


                $.each(feedbacks,function (i,feedback) {
                    tbodyInf+='<tr onclick="getUpdateFeedback('+feedback.id+')">';
                    //tbodyInf+='<td><input type="checkbox" class="check_item" value="'+feedback.id+'"/></td>';
                    tbodyInf+='<td>'+feedback.period+'</td>';
                    tbodyInf+='<td>'+feedback.title+'</td>';
                    tbodyInf+='<td>'+feedback.questionUser.logname+'</td>';
                    if(feedback.answer==null) {
                        tbodyInf += '<td>' + "未读" + '</td>';
                    }else{
                        tbodyInf += '<td>' + "已读并回复" + '</td>';
                    }
                    tbodyInf+='</tr>';

                    pageSize++;
                    //中止循环
                    if(pageSize==pageInfo.pageSize){
                        return false;
                    }
                });
                // tbodyInf+='</div>';

                pageInf+='<li><a href="#" onclick="getReplyMessageQuery(1)">首页</a></li>';
                if(pageInfo.hasPreviousPage){
                    pageInf+='<li><a href="#" onclick="getReplyMessageQuery('+(pageNumber-1)+')">上一页</a></li>';
                }

                $.each(pageInfo.navigatepageNums,function (index,item) {
                    if(item==pageNumber){
                        pageInf+='<li class="active"><a href="#">'+item+'</a></li>';
                    }else {
                        pageInf+='<li><a href="#" onclick="getReplyMessageQuery('+item+')">'+item+'</a></li>';
                    }
                });

                if(pageInfo.hasNextPage){
                    pageInf+='<li><a href="#" onclick="getReplyMessageQuery('+(pageNumber+1)+')">下一页</a></li>';
                }
                pageInf+='<li><a href="#" onclick="getReplyMessageQuery('+pageInfo.pages+')">末页</a></li>';

                $("#Msg").html(tbodyInf);
                $("#pageInf").html(pageInf);


            }
        });
    }


    function getDetailFeedback(id) {

        $.ajax({
            url:"${APP_PATH}/feedback/get",
            type:"get",
            data:{
                id:id
            },
            success:function(result) {
                var feedback=result.body.feedback;
                $("#detail_feedback_period").html(feedback.period);
                $("#detail_feedback_questionUser").html(feedback.questionUser.logname);
                $("#detail_feedback_answerUser").html(feedback.answerUser.logname);
                $("#detail_feedback_title").html(feedback.title);
                $("#detail_feedback_question").html(feedback.question);
                $("#detail_feedback_answer").html(feedback.answer);
                $("#detail_modal_del_btn").attr("idClass",feedback.id);
            }
        });
        $("#detail_modal").modal('show');
    }


    function getUpdateFeedback(id) {

        $.ajax({
            url:"${APP_PATH}/feedback/get",
            type:"get",
            data:{
                id:id
            },
            success:function(result) {
                var feedback=result.body.feedback;
                $("#update_feedback_period").html(feedback.period);
                $("#update_feedback_questionUser").html(feedback.questionUser.logname);
                $("#update_feedback_answerUser").html(feedback.answerUser.logname);
                $("#update_feedback_title").html(feedback.title);
                $("#update_feedback_question").html(feedback.question);
                //$("#detail_feedback_answer").html(feedback.answer);
                $("#detail_modal_update_btn").attr("idClass",feedback.id);
            }
        });
        $("#update_modal").modal('show');
    }

    $("#detail_modal_del_btn").click(function(){
        var id= $("#detail_modal_del_btn").attr("idClass");
        del_feedback(id);
    });

    $("#detail_modal_update_btn").click(function(){
        var id= $("#detail_modal_update_btn").attr("idClass");
        var answer=$("#update_feedback_answer").val();
        update_feedback(id,answer);
    });

    function del_feedback(id){
        $.ajax({
            url:"${APP_PATH}/feedback/delete",
            type:"post",
            data:{
                id:id
            },
            success:function(result) {
                $("#detail_modal").modal('hide');

            }
        });
        getSendMessageQuery(1);
    }

    function update_feedback(id,answer){
        $.ajax({
            url:"${APP_PATH}/feedback/replyFeedback",
            type:"post",
            data:{
                id:id,
                answer:answer
            },
            success:function(result) {
                if(result.success){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                }else{
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                }



            }
        });
        $("#update_modal").modal('hide');
        getReplyMessageQuery(1);
    }


    $("#writeFeedback_btn").click(function(){
        var questionUserLogname= $("#logname").html();
        var answerUserLogname= $("#write_feedback_answerUser").val();
        var title= $("#write_feedback_title").val();
        var question= $("#write_feedback_question").val();
        $.ajax({
            url:"${APP_PATH}/feedback/sendFeedback",
            type:"post",
            data:{
                questionUserLogname:questionUserLogname,
                answerUserLogname: answerUserLogname,
                title:title,
                question:question

            },
            success:function(result) {
                if(result.success){
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                }else{
                    layer.msg(result.msg,{time:3000,icon:3,shift:6});
                }
            }
        });
        $("#write_modal").modal('hide');
        getSendMessageQuery(1);
    });

</script>
</body>
</html>
