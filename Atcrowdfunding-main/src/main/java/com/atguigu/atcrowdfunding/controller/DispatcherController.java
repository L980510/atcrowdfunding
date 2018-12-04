package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/***
 * 首页的跳转
 */
@Controller
public class DispatcherController {

    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 在首页点击登入按钮跳转到登入页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    /***
     * 登入操作(同步请求)
     */
  /* @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String type,
                          Map<String,Object>map, HttpSession session){
       //接受请求参数封装到map集合中之后再调用业务方法
       map.put("loginAcct",loginacct);
       map.put("userPswd",userpswd);
       map.put("type",type);
       //调用业务方法（登入方法处理请求）
       User user = userService.queryUserlogin(map);
       //将处理的结果存储到session中
       session.setAttribute(Const.LOGIN_USER,user);
       //最后重定向到登入后的页面
       //为什么要重定向，而不是直接跳转：
       //因为如果直接跳转，会在次刷新登入页面以防表单重复提交
       //如果重定向他不会刷新登入页面，只会刷新main页面
       return "redirect:/main.htm";
   }*/

    /**
     * 异步请求登入(并且要返回消息)
     * @param loginacct
     * @param userpswd
     * @param type
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/doLogin")
    //@ResponseBody 结合jackson组件，将返回结果转换成字符串
    //将json串以流的形式返回客户端
    @ResponseBody
    //注意：当页面传过来的参数名称和控制层方法接受的参数名称不一致的时候
    //解决办法是和页面传入的参数name属性值的名称一致
    //如果不一致，可以使用@RequestParam来设置去别名和页面的那么属性相同的值
    public Object doLogin(@RequestParam("loginAcct") String loginacct,
                          @RequestParam("userPswd") String userpswd, @RequestParam("type") String type,
                          Map<String,Object>map, HttpSession session){
        AjaxResult ajaxResult=new AjaxResult();
        try {
            //接受请求参数封装到map集合中之后再调用业务方法
            map.put("loginAcct",loginacct);
            map.put("userPswd", MD5Util.digest(userpswd));
            map.put("type",type);
            //调用业务方法（登入方法处理请求）
            User user = userService.queryUserlogin(map);
            //共享session
            session.setAttribute(Const.LOGIN_USER,user);
            ajaxResult.setSuccess(true);
            Permission rootPermission = null;
            //注意：在登录的是后就好查询该用户对应多少许可权限
            List<Permission> myPermissions = permissionService.getPermissionByUserId(user.getId());
            Map<Integer, Permission> hashMap = new HashMap<Integer, Permission>();
            Set<String> authurls=new HashSet();
            for (Permission innerPermission : myPermissions) {
                hashMap.put(innerPermission.getId(), innerPermission);
                if (StringUtil.isNotEmpty(innerPermission.getUrl())) {
                    authurls.add("/"+innerPermission.getUrl());
                }
            }
            for (Permission permission : myPermissions) {
                //将菜单设置为子菜单
                Permission child = permission;
                //判断该子菜单pid是否为null如果为空则添加到父菜单中
                if (child.getPid() == null) {
                    rootPermission = permission;
                } else {
                    //如果pid不为空通过子类他的pid查询到父类
                    Permission parent = hashMap.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }
            session.setAttribute(Const.LOGIN_AUTH_USER_AUTHURLS, authurls);
            session.setAttribute(Const.LOGIN_AUTH_USER_PERMISSION, rootPermission);
        }catch (Exception e){
          e.printStackTrace();
          ajaxResult.setSuccess(false);
          //{success:true,message："登录失败"}
          ajaxResult.setMessage("登录失败");
        }
        //最后重定向到登入后的页面
        //为什么要重定向，而不是直接跳转：
        //因为如果直接跳转，会在次刷新登入页面以防表单重复提交
        //如果重定向他不会刷新登入页面，只会刷新main页面
        return ajaxResult;
    }
    /**
     * 登入账号密码之后跳转的页面
     */
    @RequestMapping("/main")
    public String main(HttpSession session) {




        return "main";
    }
    /**
     * 注销
     */
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session){
       // session.invalidate();//销毁session或者清理session域
        return "redirect:index.htm";
    }


    /**
     * 在首页点击注册按钮跳转到注册页面
     * @return
     */
    @RequestMapping("/reg")
    public String register(){
        return "reg";
    }
    /**
     * 注册方法
     * @return
     */
    @RequestMapping("doReg")
    @ResponseBody
    public Object doReg(@RequestParam("loginAcct") String loginacct,
    @RequestParam("userPswd") String userpswd, @RequestParam("acctType") String acctType,
    @RequestParam("email") String email,
    Map map){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数，并且进行封装到map
            map.put("loginAcct",loginacct);
            map.put("userPswd",userpswd);
            map.put("acctType",acctType);
            map.put("email",email);
            //之后通过调用业务层方法进行业务处理
            int member=memberService.memberRegistered(map);
            ajaxResult.setSuccess(true);
        }catch(Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("注册失败");
        }
         //最后跳转页面
      return ajaxResult;
    }

    @RequestMapping("member")
    public String member(){
        return "member";
    }
    //错误页面
    @RequestMapping("error")
    public String error(){
        return "error/error";
    }

}
