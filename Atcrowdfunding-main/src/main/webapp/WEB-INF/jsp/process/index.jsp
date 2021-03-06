
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${APP_PATH}/jquery/pagination/lib/pagination.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>

                    <button id="uploadProcDefBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-upload"></i> 上传流程定义文件</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <%--文件上传表单--%>
                        <form id="deployFrom" action="" method="post" enctype="multipart/form-data" >
                            <input id="processDefFile" type="file" name="processDefFile" style="display: none">
                        </form>
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>流程定义名称</th>
                                <th>流程定义版本</th>
                                <th>流程定义Key</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">

                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/pagination/lib/jquery.pagination.js"></script>
<script src="${APP_PATH}/jquery/jquery-form/jquery-form.min.js"></script>
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
        <c:if test="${empty param.currentPage}">
        queryPageUser(0);
        </c:if>
        <c:if test="${not empty param.currentPage}">
        queryPageUser(${param.currentPage}-1);
        </c:if>
        //调用分页请求
        /* queryPageUser(0);*/
        ///*后台页面点击菜单并且选中该菜单标红和菜单栏展开*/
        showMenu();


    });
    $("tbody .btn-success").click(function(){
        window.location.href = "${APP_PATH}/assignRole.htm";
    });
    $("tbody .btn-primary").click(function(){
        window.location.href = "edit.html";
    });

    //====================
    function pageChange(currentPage) {
        // window.location.href ="${APP_PATH}/user/list.htm?currentPage="+currentPage;
        queryPageUser(currentPage-1);
        //alert("1111");
    };
    jsonObj={
        currentPage:1,
        pageSize:10
    }
    var loadingIndex=-1;

    function queryPageUser(pageIndex) {
        //alert("2222");
        jsonObj.currentPage = pageIndex+1;
        if(condition){
            jsonObj.pagetext = $("#queryText").val(); //增加模糊查询条件
        }
        $.ajax({
            type: "post",
            data: jsonObj,
            url: "${APP_PATH}/process/list.do",

            beforeSend: function () {
                //一般做表单数据校验
                loadingIndex = layer.load(2, {time: 10 * 1000})
                return true;
            },
            success: function (ajaxResult) {
                layer.close(loadingIndex);
                if (ajaxResult.success == true) {
                    //如果状态为4状态码为200
                    //获取业务层封装好的数据
                    var page = ajaxResult.page;
                    var datas = page.datas;
                    //进行拼接数据
                    var content = '';
                    //i是索引，n是拿到循环出来的对象
                    $.each(datas, function (i, n) {
                        content += '<tr>';
                        content += '  <td>' + (i + 1) + '</td>';
                        content += '  <td><input   type="checkbox" id="'+n.id+'" name="'+n.name+'"></td>';
                        content += '  <td>' + n.name + '</td>';
                        content += '  <td>' + n.version + '</td>';
                        content += '  <td>' + n.key + '</td>';
                        content += '  <td>';
                        //content += '<button type="button" onclick=\'window.location.href="${APP_PATH}/user/assignRole.htm?id=""\' class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                        // content+='	  <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                        content+='	  <button type="button" onclick="window.location.href=\'${APP_PATH}/process/toShow.htm?id='+n.id+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                        content+='	  <button type="button" class="btn btn-danger btn-xs" onclick="deleteUser(\''+n.id+'\',\''+n.name+'\')"><i class=" glyphicon glyphicon-remove"></i></button>';
                        /* content += '	  <button type="button" class="btn btn-danger btn-xs><i class=" glyphicon glyphicon-remove"></i></button>';*/
                        content += '  </td>';
                        content += '</tr>';
                    });
                    //将拼接好的数据添加到tbody标签中
                    $("tbody").html(content);
                    //=====拼接分页数据==============
                    //var contentBar = '';
                    /*/!*上一页*!/
                    if (page.currentPage == 1) {
                        contentBar += '<li class="disabled"><a href="#">上一页</a></li>';
                    } else {
                        contentBar += '<li><a href="#" onclick="pageChange(' + (page.currentPage - 1) + ')" >上一页</a></li>';

                    }
                    /!*导航条*!/
                    for (var i = 1; i <= page.totalno; i++) {
                        contentBar += '<li';
                        if (page.currentPage == i) {
                            contentBar += ' class="active"';
                        }
                        contentBar += '><a href="#" onclick="pageChange(' + i + ')">' + i + '</a></li>'
                    }
                    /!*下一页*!/
                    if (page.currentPage == page.totalno) {
                        contentBar += '<li class="disabled"><a href="#">下一页</a></li>';
                    } else {
                        console.log("11111" + page.totalno);
                        contentBar += '<li><a href="#" onclick="pageChange(' + (page.currentPage + 1) + ')" >下一页</a></li>';

                    }
                    /!*最后将分页到拼接添加到这个pagination中去*!/
                    $(".pagination").html(contentBar);*/
                    // 创建分页
                    var num_entries =page.totalCount;
                    $("#Pagination").pagination(num_entries, {
                        num_edge_entries: 2, //边缘页数
                        num_display_entries: 4, //主体页数
                        callback: queryPageUser,
                        items_per_page:page.pageSize, //每页显示1项
                        current_page:(page.currentPage-1),
                        prev_text : "上一页",
                        next_text : "下一页",

                    });
                } else {
                    layer.msg(ajaxResult.message, {time: 1000, icon: 5, shift: 6})
                }

            },
            error: function () {
                layer.msg("加载数据失败", {time: 1000, icon: 5, shift: 6});
            }
        });
    }
    var condition = false ;
    /*条件查询*/
    $("#queryBtn").click(function () {
        //要拿到用户输入的值
        var queryText=$("#queryText").val();
        jsonObj.queryText=queryText;
        condition = true ;
        //因为你条件查询她显示的还是显示第一页
        pageChange(1);
    })
    //删除
    function deleteUser(id,name) {
        var loadingIndex=-1;
        layer.confirm("你确定要删除["+name+"]该流程定义对象？",{icon:3,title:"删除提示"},function (cindex) {
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/process/toDelete.do",
                data:{
                    id:id
                },
                beforeSend:function () {
                    //校验表单数据
                    loadingIndex=layer.load("正在删除数据",{time:1000,icon:6});
                    return true;
                },
                success:function (ajaxResult) {
                    if (ajaxResult.success==true) {
                        layer.msg("删除成功！",{time:1000,icon:5,shift:6});
                        //删除成功跳转到用户tolist页面
                        queryPageUser(0);
                    }else{
                        layer.msg(ajaxResult.message,{time:1000,icon:5,shift:6});
                    }
                },
                error:function () {
                    layer.msg("删除失败",{time:1000,icon:5,shift:6});
                }
            });

            layer.close(cindex);
        },function (cindex) {
            layer.close(cindex);
        })

    }

    //=============
    //===============
    //部署流程定义对象
    //为按钮绑定点击事件
    //click()函数增加回调函数这个参数，表示绑定事件
    $("#uploadProcDefBtn").click(function () {
        //click()函数没有参数，表示触发单击事件
        $("#processDefFile").click();
    });
    //=======================
    //为文件上传绑定改变事件
    $("#processDefFile").change(function () {
        var indexload=-1;
        var options={
            url:"${APP_PATH}/process/deploy.do",

            beforeSubmit:function () {
                indexload=layer.message("数据正在部署中",{icon:5});
                return true;
            },
            success:function (ajaxResult) {
                layer.close(indexload);
                if (ajaxResult.success) {
                    layer.msg("部署成功",{time:1000,icon:5,shift:6});
                    //跳回首页
                    queryPageUser(0);
                }else{
                    layer.msg("部署失败",{time:1000,icon:5,shift:6});
                }
            }
        };
        //异步提交
        $("#deployFrom").ajaxSubmit(options);
         return;


    });


</script>
<script type="text/javascript" src="${APP_PATH }/script/menu.js"></script>

</body>
</html>

