package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限拦截，为授权禁止访问系统相关资源
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{
    @Autowired
    private PermissionService permissionService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      //获取用户请求得地址
        String requestURI = request.getRequestURI();
        //1.先获取给系统所有许可权限，判断该权限地址是否在系统权限系统中如果在放行
        /*List<Permission> permissions = permissionService.queryAllPermissions();
        Set<String>allPermission=new HashSet<String>();
        for (Permission permission : permissions) {
            if (StringUtil.isNotEmpty(permission.getUrl())) {
                allPermission.add("/"+permission.getUrl());
            }
        }*/
        ServletContext application = request.getSession().getServletContext();
        Set<String>  allPermission= (Set<String>)application.getAttribute(Const.LOGIN_AUTH_USER_PERMISSION);
        if (allPermission.contains(requestURI)) {
            // /2拿到用户地址与该用户许可权限中的权限地址进行比较
            //如果包含就放行
            //所以需要查询对应登入用户所拥有的权限
            HttpSession session = request.getSession();

            Set<String> authurls = (Set<String>)session.getAttribute(Const.LOGIN_AUTH_USER_AUTHURLS);
            if (authurls.contains(requestURI)) {
                return true;//如果该用户存在该权限放行
            }else{
                response.sendRedirect(request.getContextPath()+"/error.htm");
                return false;//如果该访问的地址在系统地址中但是不在该登入用户的权限中不放行

            }
        }else{
            //如果该访问的地址不在所有的系统地址中，也放行
            //也就是说不需要权限访问，直接放行
            return true;
        }

    }
}
