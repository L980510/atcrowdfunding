package com.atguigu.atcrowdfunding.listener;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 *解决上下文路径使用
 */
public class StartSystemListener implements ServletContextListener {
    //在服务器启动时，创建application对象时候需要咨询的方法
    //注意你需要这个类起作用，那么就需要在web.xml文件中进行配置给监听器
    public void contextInitialized(ServletContextEvent sce) {
        //1.将项目上下文路径（request.getContextPath()放置到application作用域中）
        ServletContext application = sce.getServletContext();
        //通过拿到ServletContext对象来拿到上下文路径
        String contextPath = application.getContextPath();
        //之后将上下文路径共享到作用域中在页面就可以拿到了
        application.setAttribute("APP_PATH",contextPath);
        System.out.println("APP_PATH"+contextPath);
        //========================================
        //解决拦截器使用到系统所有许可信息是每一次请求都会使用到的数据，不必名称都必须去查询数据
        //库，否则效率太低了
        //使用监听器，在服务器启动时候就将所有许可信息存放到application域/二级缓存中，提供该拦截器使用
        //解决方法：
        //获取系统所有权限菜单肚子
        ApplicationContext ico = WebApplicationContextUtils.getWebApplicationContext(application);
        PermissionService permissionService = ico.getBean(PermissionService.class);
        List<Permission> allPermissions = permissionService.queryAllPermissions();
        Set<String> hashSet = new HashSet();
        for (Permission permission : allPermissions) {
            hashSet.add("/"+permission.getUrl());
        }
        application.setAttribute(Const.LOGIN_AUTH_USER_PERMISSION,hashSet);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
