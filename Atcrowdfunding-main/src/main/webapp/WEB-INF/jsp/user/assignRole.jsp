<%--
  Created by IntelliJ IDEA.
  User: intel
  Date: 2018/11/21
  Time: 9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="${APP_PATH}/user/tolist.htm">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
            </ul>
            <form class="navbar-form navbar-right">
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
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select class="form-control" id="leftRoleList" multiple size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${leftList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="leftToRoleListBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="rightToRoleListBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select id="rightRoleList"  class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${rightList}" var="role">
                                <option value="${role.id}">${role.name}</option>

                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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



        //功能为用户选择角色分配角色saveUserRoleRelation
        $("#leftToRoleListBtn").click(function () {
            var leftRoleItems=$("#leftRoleList :selected");
            if (leftRoleItems.length==0) {
                layer.msg("请选择添加角色！",{time:1000,icon:5,shift:6});
            }else{
                //要把前端传过来的用户id和相应添加的角色id
                var dataObj={userid:"${param.id}"};
                $.each(leftRoleItems,function (i,n) {
                    dataObj["ids["+i+"]"]=this.value;
                    console.log("-------"+dataObj);

                });
            }
            var loadingIndex=-1;
            $.ajax({
                data:dataObj,
                type:"POST",
                url:"${APP_PATH}/user/saveUserRoleRelation.do",
                beforeSend:function () {
                    loadingIndex=layer.load("正在为用户添加角色",{time1:100,icon:5,shift:6});
                    return true;
                },
                success:function (ajaxResult) {
                    if (ajaxResult.success==true) {
                        layer.msg("添加角色成功！",{time:1000,icon:5,shift:6});
                        //拿到左边选中的角色添加到右边的角色框
                        //var selectOptionLeftRoleList=$("#leftRoleList option:selected");
                        //添加到右边选择框中
                        $("#rightRoleList").append(leftRoleItems.clone());
                        //并且把左边的删除
                        leftRoleItems.remove();
                        window.location.href="${APP_PATH}/user/tolist.htm";
                    }else{
                        layer.msg(ajaxResult.message,{time:1000,icon:5,shift:6});
                    }
                },
                error:function () {
                    layer.msg("操作失败",{time:100,icon:5,shift:6});
                }
            })

        })

        //将右边的角色移动到左边=======
        //功能为用户选择角色
        $("#rightToRoleListBtn").click(function () {
            var selectOptionRightRoleList=$("#rightRoleList :selected");
            if (selectOptionRightRoleList.length==0) {
                layer.msg("请选择取消的角色",{time:100,icon:5,shift:6});
            }
           var dataObjects={userid:"${param.id}"};
            $.each(selectOptionRightRoleList,function (i,n) {
                //先将用户id设置到要封装的数据中之后再将要取消的角色id
                console.log(this.value);
                dataObjects["ids["+i+"]"]=this.value;
            })

            $.ajax({
                url:"${APP_PATH}/user/deleteUserRoleRelation.do",
                type:"POST",
                data:dataObjects,
                beforeSend:function () {
                    loadingIndex=layer.load("正在为用户取消角色",{time1:100,icon:5,shift:6});
                    return true;
                },
                success:function (ajaxResult) {
                   if (ajaxResult.success==true) {
                       layer.msg("取消角色成功！",{time:1000,icon:5,shift:6});
                       //拿到左边选中的角色添加到右边的角色框
                       //添加到右边选择框中
                       $("#leftRoleList").append(selectOptionRightRoleList.clone());
                       //并且把左边的删除
                       selectOptionRightRoleList.remove();
                       window.location.href="${APP_PATH}/user/tolist.htm";
                   }else{
                       layer.msg(ajaxResult.message,{time:100,icon:5,shift:6});
                   }

                },
                error:function () {
                    layer.msg("操作失败",{time:100,icon:5,shift:6});
                }

            })

        })
    });



</script>
</body>
</html>
