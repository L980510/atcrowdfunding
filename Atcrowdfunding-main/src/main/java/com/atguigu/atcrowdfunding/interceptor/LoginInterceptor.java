package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录拦截器，未登入系统禁止范围相关资源
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求url
        String requestURI = request.getRequestURI();
        //设置可以访问的白名单（添加白名单）
       Set<String> urls = new HashSet();
       urls.add("/doLogin.do");
       urls.add("/login.htm");//必须添加到白名单，否则，死循环
       urls.add("/index.htm");
       urls.add("/loginOut.do");
       urls.add("/reg.htm");
       urls.add("/doReg.do");
        if (urls.contains(requestURI)) {
            return true;
        }else {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute(Const.LOGIN_USER);
            if (user==null) {
                response.sendRedirect(request.getContextPath()+"/login.htm");
                return false;
            }else {
                return true;
            }
        }
    }
}
