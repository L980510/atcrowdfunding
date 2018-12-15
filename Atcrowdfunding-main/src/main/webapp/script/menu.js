/*后台页面点击菜单并且选中该菜单标红和菜单栏展开*/
function showMenu() {
    //1需要拿到/user/tolist.htm
    //先拿到菜单的连接
   var href=window.location.href;
   //因为idea部署项目就在tomcat的root目录下所以没有带项目名称
  // alert(href);//http://localhost/user/tolist.htm
   var host=window.location.host;
   //alert(host);//localhost
    //在进行拿到/的索引进行截取（/的索引是http：//localhost）
    //获取host的长度+http://
    //而http：//这么截取要拿到host的索引进行截取
    var index=href.indexOf(host);//7那到的正好是host中l的索引长度
    var path=href.substring(index+host.length);//正好拿到了我们需要的地址/user/tolist.htm
    var contextPath="${APP_PATH}";
   //这边是考虑当项目部署到服务器时候地址可能改变
    //因为idea部署项目就在tomcat的root目录下所以没有带项目名称
    if ($.trim(contextPath.length)=="APP_PATH"){
        var pathAddress=path.substring(contextPath.length);
        //为链接href添加点击那个菜单就添加那个地址
        var alink=$(".list-group a[href*='"+pathAddress+"']");
    }else{
        //为链接href添加点击那个菜单就添加那个地址
        var alink=$(".list-group a[href*='"+path+"']");
    }
   //为选择中的链接标红也就是添加css属性
    alink.css("color","red");
    //如果有该属性tree-closed该菜单将会收起，如果没有则会展示该菜单下面的子菜单
    //alink是所有的a标签你需要找到<li class="list-group-item tree-closed">
    alink.parent().parent().parent().removeClass("tree-closed");
    alink.parent().parent().show();
}
