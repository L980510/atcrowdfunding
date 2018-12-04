<%--
  Created by IntelliJ IDEA.
  User: intel
  Date: 2018/11/17
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" type="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.htm">众筹平台 - 分类管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
            </ul>
            <form  class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
               <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form id="addForm" >
                        <div class="form-group">
                            <label for="fname">分类名称</label>
                            <input type="text" class="form-control" id="fname" placeholder="请输入分类名称">
                        </div>
                        <div class="form-group">
                            <label for="fremark">分类备注</label>
                            <input type="text" class="form-control" id="fremark" placeholder="请输入分类备注">
                        </div>
                        <button id="saveBtn"  type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button id="resetBtn"  type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" type="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
   $("#saveBtn").click(function () {

       var fname=$("#fname").val();
       var fremark=$("#fremark").val();
       //对于表单数据而已不能用null进行判断，
       //如果文本框什么都没有输入，获取的是""
       if ($.trim(fname)=="") {
           layer.msg("名称不能为空,请重新输入！", {time:1000, icon:5, shift:6}, function () {
               //将之前分类输入的数据清除
               $("#fname").val("")
               fname.focus();
           });
           return false;
       }
       if ($.trim(fremark)=="") {
           layer.msg("名称不能为空,请重新输入！", {time:1000, icon:5, shift:6}, function () {
               //将之前分类输入的数据清除
               $("#fremark").val("")
               fremark.focus();
           });
           return false;
       }

       var loadingIndex=-1;
       $.ajax({
            url:"${APP_PATH}/type/doAdd.do",
            data:{
                name:fname,
                remark:fremark
            },
           type:"POST",
           beforeSend:function () {
               //表单之前验证
               loadingIndex=layer.load(2,{time:10*1000})
               return true;
           },
           success:function (ajaxResult) {
               layer.close();
               if (ajaxResult.success==true) {
                   layer.msg("添加成功",{time:1000,icon:5,shift:6});
                   //添加成功之后进行跳转到分类list页面
                   window.location.href="${APP_PATH}/type/tolist.htm";
               }else {
                   layer.msg(ajaxResult.message,{time:1000,icon:5,shift:6});
               }
           },
           error:function () {
               layer.msg("添加失败",{time:1000,icon:5,shift:6});
           }
       })

   })
    //============重置==============================
    //先为重置按钮绑定点击事件
    //2,你要这么做点击按钮之后表单数据清空
    //那需要你拿到表单对象进行清空
    $("#resetBtn").click(function () {
        //alert("3333");
        $("#addForm").get(0).reset();//现将jquery对象转换为dom对象之后调用清空输入框
    })
</script>
</body>
</html>
