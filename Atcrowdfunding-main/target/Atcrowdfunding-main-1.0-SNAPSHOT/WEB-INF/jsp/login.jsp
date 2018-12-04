<%--
  Created by IntelliJ IDEA.
  User: intel
  Date: 2018/11/8
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">

    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.htm" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="loginForm" action="${APP_PATH}/doLogin.do" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="floginAcct" name="loginAcct" value="superAdmin" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="fuserPswd" name="userPswd" value="admin" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select id="ftype" class="form-control" name="type">
                <option value="member">会员</option>
                <option value="user" selected>管理</option>
            </select>
        </div>
        ${exception.message }
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="reg.htm">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script>
    function dologin() {
        var floginAcct=$("#floginAcct").val();
        var fuserPswd=$("#fuserPswd").val();
        var ftype=$("#ftype").val();
        //对于表单数据而已不能用null进行判断，
        //如果文本框什么都没有输入，获取的是""
        if ($.trim(floginAcct)=="") {
            //alert("账号不能为空")
            layer.msg("账号不能为空,请重新输入！", {time:1000, icon:5, shift:6}, function () {
                //将之前用户输入的数据清除
                $("#floginAcct").val("")
                floginAcct.focus();
            });
            return false;



        }
        if ($.trim(fuserPswd)=="") {
            layer.msg("密码不能为空,请重新输入！",{time:1000,icon:5,shift:6},function () {
                $("#fuserPswd").val("")
                fuserPswd.focus();
                //return false;这个结束循环的操作不能回调函数中否则无法结束if语句
            })

            return false;
        }
        console.log(floginAcct,fuserPswd,ftype+"1111111");
        var layerIdex=1;
        $.ajax({
            url:"${APP_PATH}/doLogin.do",
            /*为什么使用post请求：因为如果是get请求密码会不安全*/
            type:"POST",
            data:{
                loginAcct:floginAcct,
                userPswd:fuserPswd,
                type:ftype

            },
            beforeSend:function () {
                //一般做表单数据校验
                var layerIdex=layer.msg('正在登入......',{icon:16})
                return true;

            },
            success:function (ajaxResult) {
                layer.close();
                if(ajaxResult.success==true){
                   // alert("ok")
                    //跳转主页面
                    window.location.href="${APP_PATH}/main.htm"
                }else {
                    //alert("failure");
                    var msg=ajaxResult.message;
                    //console.log("msg"+msg);
                    layer.msg(msg,{time:1000,icon:5,shift:6})
                    //如果输入错误--->跳回登入页面
                    //window.location.href="${APP_PATH}/login.htm"
                }
            },
            error:function () {
               // alert("error")
                layer.msg("登入失败",{time:1000,icon:5,shift:6})
            }
        });
       //$("#loginForm").submit();
        /*var type = $(":selected").val();
        if ( type == "user" ) {
            window.location.href = "main.htm";
        } else {
            window.location.href = "index.htm";
        }*/
    }
</script>
</body>
</html>
