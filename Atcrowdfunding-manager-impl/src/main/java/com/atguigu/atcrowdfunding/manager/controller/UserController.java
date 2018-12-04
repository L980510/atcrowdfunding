package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowdfunding.vo.DateVo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户控制层
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    /**
     * 查询后台用户管理分页页面(同步)
     */
   /* @RequestMapping("/list")
    public String queryPageUser(@RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                Map map){
        //获取请求参数进行封装到分页对象中
        PageResult pageResult =new PageResult(currentPage,pageSize);
        //调用业务方法处理请求
        PageResult userPageResult=userService.queryPageUser(pageResult);
        //将处理好的业务请求进行共享数据
        map.put("userPageResult",userPageResult);
        //页面拿到数据进行渲染
        return "user/index";
    }*/

    @RequestMapping("tolist")
    public String tolist(){
        return "user/index";
    }
    //为用户添加角色（显示角色）
    @RequestMapping("assignRole")
    public String assignRole(Integer id,Map map){
        //先查询出所有角色
        List<Role> roleList =roleService.selectAll();
        //在通过要用户id查询到他对应的角色的id
        List <Integer>RoleId=userService.selectByUserIdRoleData(id);
        //定义两个集合一个是用来装左边选项框的数据，另一个是来装右边数据
        List<Role> leftList = new ArrayList();
        List<Role> rightList = new ArrayList();
        //右边选项框是放之前分配好给用户的权限
        for (Role role : roleList) {
            //如果通过用户查询全部的角色id和查询全部中的角色id相同就属于右边框
           if (RoleId.contains(role.getId())){
              rightList.add(role);
           }else {
               //左边是放没有分配给用户的权限
               leftList.add(role);
           }
        }
        //之后将业务层处理好的数据返回回来之后将他共享到页面进行响应用户
        map.put("leftList",leftList);
        map.put("rightList",rightList);

        return "user/assignRole";
    }
    //分配角色saveUserRoleRelation

    //取消角色deleteUserRoleRelation


    /**
     * 异步加载数据
     * @param currentPage
     * @param pageSize
     * @param
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object queryPageUser(@RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                              String queryText,Map<String,Object> map ){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            //获取请求参数进行封装到分页对象中
            //PageResult pageResult =new PageResult(currentPage,pageSize);
            map.put("currentPage",currentPage);
            map.put("pageSize",pageSize);
            if (StringUtil.isNotEmpty(queryText)) {
                if (queryText.contains("%")){
                    queryText.replaceAll("%","////%");
                }
                map.put("queryText",queryText);

            }
            //调用业务方法处理请求
            PageResult userPageResult=userService.queryPageUser(map);
            //将处理好的业务请求进行共享数据
            ajaxResult.setSuccess(true);
            ajaxResult.setPage(userPageResult);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage(e.getMessage());
        }

        //页面拿到数据进行渲染
        return ajaxResult;
    }

   //添加
    @RequestMapping("add")
    public String add(){
        return "user/toAdd";
    }

    @RequestMapping("toAdd")
    @ResponseBody
    public Object toAdd(User user){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            int insert = userService.insert(user);
            ajaxResult.setSuccess(insert==1);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }
    //更新操作
    @RequestMapping("update")
    public String update(Integer id,Map map){
        //因为编辑跳转到对应的页面需要回显数据所以需要通过id查询对象信息在通过模板共享到页面
        if (id!=null) {
            User user = userService.selectByPrimaryKey(id);
            map.put("user",user);
        }
        return "user/toUpdate";
    }

    @RequestMapping("toUpdate")
    @ResponseBody
    public Object toUpdate(User user){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            int insert = userService.updateByPrimaryKey(user);
            ajaxResult.setSuccess(insert==1);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }




    @RequestMapping("toDelete")
    @ResponseBody
    public Object toDelete(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if (id!=null) {
                int deleteUser = userService.deleteByPrimaryKey(id);
                ajaxResult.setSuccess(deleteUser==1);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    //批量删除
    @RequestMapping("toBatchDeleteUser")
    @ResponseBody
    public Object toBatchDelete(DateVo data){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if (data.getUserList().size()!=0) {
                int toBatchDeleteUser=userService.toBatchDeleteUser(data);
                ajaxResult.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    //批量删除（）
    @RequestMapping("toBatchDelete")
    @ResponseBody
    public Object toBatchDelete(@RequestParam("id") Integer []ids){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if (ids.length!=0) {
                int toBatchDeleteUser=userService.toBatchDelete(ids);
                ajaxResult.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    //功能为用户选择角色分配角色saveUserRoleRelation（批量为用户添加角色）
    @RequestMapping("saveUserRoleRelation")
    @ResponseBody
    public Object saveUserRoleRelation(@RequestParam("userid") Integer userid, DateVo data) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int insertRoles = userService.saveUserRoleRelation(userid, data);
            if (insertRoles==data.getIds().size()) {
                ajaxResult.setSuccess(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage("添加用户角色失败");
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }
    //取消为用户添加的角色
    @RequestMapping("deleteUserRoleRelation")
    @ResponseBody
    public Object deleteUserRoleRelation(@RequestParam("userid") Integer userid, DateVo data) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int insertRoles = userService.deleteUserRoleRelation(userid, data);
            if (insertRoles==data.getIds().size()) {
                ajaxResult.setSuccess(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage("取消用户角色失败");
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }
}
