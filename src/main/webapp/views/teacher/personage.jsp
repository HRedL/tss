<%--
  Created by IntelliJ IDEA.
  User: 叶之于秋
  Date: 2019/5/7
  Time: 12:31
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
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/css/personage.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/css/icon/css/cropper.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/tools/student_teachaer/css/icon/css/sitelogo.css" rel="stylesheet">
    <style type="text/css">
        .stu_table {
            box-shadow: 4px 4px 10px #cfe1f9;
        }
        .cztable {
            border-top: 1px solid #cfe1f9;
        }
    </style>
    <title>EAS-个人信息界面</title>
</head>
<body>

    <jsp:include page="common/teacherTitle.jsp" />

    <div class="container-fluid">
        <div class="row">

            <jsp:include page="common/teacherMenu.jsp"/>

            <div class="rightbox"
                 style="margin-top: 60px; margin-right:250px; float: right; width: 60%">
                <h2 class="mbx" style="font-size: 15px">我的信息 &gt; 个人资料 &nbsp;&nbsp;&nbsp;</h2>
                <div class="morebt">
                    <ul id="ulStudMsgHeadTab">
                        <li><a class="tab2" onclick=""
                               href="${pageContext.request.contextPath}/teacher/show_personage">
                            个人资料
                            </a>
                        </li>
                        <li><a class="tab2" onclick=""
                               href="${pageContext.request.contextPath}/teacher/show_changePassword">
                            安全管理
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="cztable">
                    <table class="stu_table" width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="right" width="80">姓名：</td>
                            <td>${teacher.tname}&nbsp;</td>
                            <td align="right" width="90">教师号：</td>
                            <td>${teacher.tnum}&nbsp;</td>

                            <td rowspan="6">
                                <div align="center">
                                    <img alt="" id="icon" height="160" width="160"
                                         src="${pageContext.request.contextPath}/tools/student_teachaer/Images/Student/photo.jpg"
                                         style="padding:2px 2px 5px; border:1px #ddd solid;">
                                    <br><br>
                                    <input type="hidden" name="iconPic" value="" id="iconPic">
                                    <div style="font-size: 15px" class="change_headPortrait same_click" data-toggle="modal" data-target="#avatar-modal" >
                                        更换头像
                                    </div>
                                </div>&nbsp;
                                <!--头像-->
                                <!--<div class="rs_content_headPortrait" align="center" id="pic_face">
                                    <img height="160" width="120" style="padding:2px 2px 5px; border:1px #ddd solid;"
                                         src="${pageContext.request.contextPath}/tools/student_teachaer/Images/Student/photo.jpg" alt="" id="icon" width="50px" height="50px"/>
                                    <input type="hidden" name="iconPic" value="" id="iconPic">
                                    <div class="change_headPortrait same_click" data-toggle="modal" data-target="#avatar-modal" >
                                        更换头像
                                    </div>&nbsp;
                                </div>-->
                            </td>
                        </tr>
                        <tr>
                            <td align="right">性别：</td>
                            <td>${sex}&nbsp;</td>
                            <td align="right">监考次数：</td>
                            <td>${sessionScope.teacher.TINVTIMES}</td>
                        </tr>
                        <tr>
                            <td align="right">院系：</td>
                            <td>${acaAndDept}&nbsp;</td>
                            <td align="right">入职时间：</td>
                            <td>2016-9-9&nbsp;</td>
                        </tr>
                        <tr>
                            <td align="right">转态：</td>
                            <td>任职&nbsp;</td>
                            <td align="right">手机号码：</td>
                            <td>${teacher.ttel}&nbsp;</td>
                        </tr>

                        <!--<tr>
                            <td colspan="4" align="left">联系方式（如联系方式有变动请及时修改，以便能及时联系到你。谢谢！）</td>

                        </tr>
                        <tr align="center">
                            <td colspan="5" height="40">
                                <div align="center">

                                    <input type="button" id="button2" value="修改联系方式" onclick="submitMail()" class="input2" />
                                </div>
                            </td>
                        </tr>-->
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 头像插件 -->
    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <!--<form class="avatar-form" action="upload-logo.php" enctype="multipart/form-data" method="post">-->
                <form class="avatar-form">
                    <div class="modal-header">
                        <button class="close" data-dismiss="modal" type="button">×</button>
                        <h4 class="modal-title" id="avatar-modal-label">上传图片</h4>
                    </div>
                    <div class="modal-body">
                        <div class="avatar-body">
                            <div class="avatar-upload">
                                <input class="avatar-src" name="avatar_src" type="hidden">
                                <input class="avatar-data" name="avatar_data" type="hidden">
                                <label for="avatarInput" style="line-height: 35px;">图片上传</label>
                                <button class="btn btn-info"  type="button" style="height: 35px;" onClick="$('input[id=avatarInput]').click();">请选择图片</button>
                                <span id="avatar-name"></span>
                                <input class="avatar-input hide" id="avatarInput" name="avatar_file" type="file"></div>
                            <div class="row">
                                <div class="col-md-9">
                                    <div class="avatar-wrapper"></div>
                                </div>
                                <div class="col-md-3">
                                    <div class="avatar-preview preview-lg" id="imageHead"></div>
                                    <!--<div class="avatar-preview preview-md"></div>
                            <div class="avatar-preview preview-sm"></div>-->
                                </div>
                            </div>
                            <div class="row avatar-btns">
                                <div class="col-md-4">
                                    <div class="btn-group">
                                        <button class="btn btn-info fa fa-undo" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees"> 向左旋转</button>
                                    </div>
                                    <div class="btn-group">
                                        <button class="btn  btn-info fa fa-repeat" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees"> 向右旋转</button>
                                    </div>
                                </div>
                                <div class="col-md-5" style="text-align: right;">
                                    <button class="btn btn-info fa fa-arrows" data-method="setDragMode" data-option="move" type="button" title="移动">
                                        <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("setDragMode", "move")">
                                        </span>
                                    </button>
                                    <button type="button" class="btn btn-info fa fa-search-plus" data-method="zoom" data-option="0.1" title="放大图片">
                                        <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("zoom", 0.1)">
                                        <!--<span class="fa fa-search-plus"></span>-->
                                        </span>
                                    </button>
                                    <button type="button" class="btn btn-info fa fa-search-minus" data-method="zoom" data-option="-0.1" title="缩小图片">
                                        <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("zoom", -0.1)">
                                        <!--<span class="fa fa-search-minus"></span>-->
                                        </span>
                                    </button>
                                    <button type="button" class="btn btn-info fa fa-refresh" data-method="reset" title="重置图片">
                                        <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper("reset")" aria-describedby="tooltip866214">
                                    </button>
                                </div>
                                <div class="col-md-3">
                                    <button id="button_save" class="btn btn-info btn-block avatar-save fa fa-save" type="button" data-dismiss="modal"> 保存修改</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/tools/student_teachaer/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/tools/student_teachaer/js/jquery.page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/tools/student_teachaer/js/personal.js"></script>
    <script src="${pageContext.request.contextPath}/tools/student_teachaer/js/icon/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/tools/student_teachaer/js/icon/cropper.js"></script>
    <script src="${pageContext.request.contextPath}/tools/student_teachaer/js/icon/sitelogo.js"></script>
    <script src="${pageContext.request.contextPath}/tools/student_teachaer/js/icon/html2canvas.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">

        //做个下简易的验证  大小 格式
        $('#avatarInput').on('change', function(e) {
            var filemaxsize = 1024 * 5;//5M
            var target = $(e.target);
            var Size = target[0].files[0].size / 1024;
            if(Size > filemaxsize) {
                alert('图片过大，请重新选择!');
                $(".avatar-wrapper").childre().remove;
                return false;
            }
            if(!this.files[0].type.match(/image.*/)) {
                alert('请选择正确的图片!')
            } else {
                var filename = document.querySelector("#avatar-name");
                var texts = document.querySelector("#avatarInput").value;
                var teststr = texts; //你这里的路径写错了
                testend = teststr.match(/[^\\]+\.[^\(]+/i); //直接完整文件名的
                filename.innerHTML = testend;
            }

        });

        $(".avatar-save").on("click", function() {
            var img_lg = document.getElementById('imageHead');
            // 截图小的显示框内的内容
            html2canvas(img_lg, {
                allowTaint: true,
                taintTest: false,
                onrendered: function(canvas) {
                    canvas.id = "mycanvas";
                    //生成base64图片数据
                    var dataUrl = canvas.toDataURL("image/png");
                    var newImg = document.createElement("img");
                    newImg.src = dataUrl;
                    imagesAjax(dataUrl)
                }
            });
        })
        function imagesAjax(src) {
            var data = {};
            data.img = src;
            $.ajax({
                url: "",
                data: data,
                type: "POST",
                dataType: 'json',
                success: function(re) {
                    if (re) {
                        if(re.status == 200) {
                            $('#icon').attr('src',re.data.url );
                            $('#iconPic').val(re.data.url);
                        }else {
                            alert("上传失败")
                        }

                    }
                }
            });
        }
    </script>
    <script type="text/javascript">
        $("#icon").click(function() {
            window.open($(this).attr("src"));
        })
    </script>
    <!--<script>
        $(".x").click(function(){
            $(".modal").hide();
        })
        $(".change_headPortrait").click(function(){
            $(".modal").show();
        })
    </script>-->
    <script>
        $("#button_save").click(function(){
            var url = $("#imageHead img").attr("src");
            $('#icon').attr('src',url);
        })

    </script>
</body>
</html>
