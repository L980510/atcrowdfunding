
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
            <div><a class="navbar-brand" href="${APP_PATH}/index.htm" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="regForm" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="loginAcct" name="loginAcct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="userPswd" name="userPswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="email" name="email" placeholder="请输入邮箱地址" style="margin-top:10px;">
            <span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" id="acctType" name="acctType" >
                <option value="0">企业</option>
                <option value="1" selected>个人</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="login.htm">我有账号</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="doReg()"> 注册</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script>
    function doReg() {
        alert("1222");
        var floginAcct=$("#loginAcct").val();
        var fuserPswd=$("#userPswd").val();
        var femail=$("#email").val();
        var facctType=$("#acctType").val();
        if ($.trim(floginAcct)=="") {
            alert("账号不能为空")
            //将之前用户输入的数据清除
            $("#loginAcct").val("")
            floginAcct.focus();
            return false;
        }
        if ($.trim(fuserPswd)=="") {
            alert("密码不能为空")
            $("#userPswd").val("")
            fuserPswd.focus();
            return false;
        }
        if ($.trim(femail)=="") {
            alert("邮箱不能为空")
            $("#email").val("")
            femail.focus();
            return false;
        }
       console.log()
        $.ajax({
            type:"POST",
            url:"${APP_PATH}/doReg.do",
            data:{
                loginAcct:floginAcct ,
                userPswd:fuserPswd,
                email:femail,
                acctType:facctType
            },
            beforeSend:function () {
               //一般做表单校验
               return true; 
            },
            success:function (ajaxResult) {
                alert("ajaxResult.success="+ajaxResult.success)
               if (ajaxResult.success==true) {
                  //跳转页面
                   window.location.href="${APP_PATH}/member.htm";
               } else {
                   //如果输入错误--->跳回注册页面
                   window.location.href="${APP_PATH}/reg.htm";
               }
            }
        })
    }
   
</script>
</body>
</html>
