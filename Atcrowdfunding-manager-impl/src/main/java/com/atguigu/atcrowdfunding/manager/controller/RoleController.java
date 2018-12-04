package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色控制层
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 跳转到角色首页
     * @return
     */
    @RequestMapping("tolist")
    public String toList(){
        return "role/index";
    }
    /**
     * 异步加载分页数据
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object doList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                         @RequestParam(value = "pageSize" ,defaultValue = "10")Integer pageSize,
                         String queryText,Map map){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            map.put("currentPage",currentPage);
            map.put("pageSize",pageSize);
            //模糊查询
            if (!StringUtils.isEmpty(queryText)) {
                if (queryText.contains("%")) {
                    queryText.replaceAll("%","////%");
                }
                map.put("queryText",queryText);
            }
            //调用业务方法处理请求
            PageResult pageResult=roleService.queryRolePageData(map);
            ajaxResult.setSuccess(true);
            ajaxResult.setPage(pageResult);
        }catch (Exception e){
            e.printStackTrace();
          ajaxResult.setSuccess(false);
          ajaxResult.setMessage(e.getMessage());
        }
        //将处理好的数据共享到页面进行显示
       return ajaxResult;

    }

    //添加角色
    @RequestMapping("add")
    public String toAdd(){
        return "role/toAdd";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Role role){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            int insert = roleService.insert(role);
            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("添加失败");

        }
        //将处理好的数据共享到页面进行显示
        return ajaxResult;

    }
    //修改
    @RequestMapping("update")
    public String update(Integer id,Map map){
        Role role = roleService.selectByPrimaryKey(id);
        map.put("role",role);
        return "role/toUpdate";
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Object doUpdate(Role role){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (role.getId()!=null) {
                int updateRole = roleService.updateByPrimaryKey(role);
                ajaxResult.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("更新失败");

        }
        //将处理好的数据共享到页面进行显示
        return ajaxResult;

    }
    //删除角色
    @RequestMapping("/toDelete")
    @ResponseBody
    public Object toDelete(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (id!=null) {
                int insert = roleService.deleteByPrimaryKey(id);
                ajaxResult.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");

        }
        //将处理好的数据共享到页面进行显示
        return ajaxResult;

    }


    //批量删除
    @RequestMapping("toBatchDeleteRole")
    @ResponseBody
    public Object toBatchDelete(DateVo data){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if (data.getRoleList().size()!=0) {
                int toBatchDeleteRole=roleService.toBatchDeleteRole(data);
                if (toBatchDeleteRole!=0) {
                    ajaxResult.setSuccess(true);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("批量删除失败");
        }
        return ajaxResult;
    }
    @RequestMapping("/assignPermission")
    public String assignPermission() {
        return "role/assignPermission";
    }
    //==========为角色分配权限(注意：如果你需要为该角色分配权限需要将之前的权限删除，
    // 之后将分配的权限重新添加到数据库中

    @RequestMapping("roleAssignPermission")
    @ResponseBody
    public Object roleAssignPermission(DateVo datas,Integer roleid){
        start();
        try{
            Integer count=roleService.saveAssignPermission(roleid,datas);
            if (count==datas.getIds().size()) {
                success(true);

            }
        }catch (Exception e){
            e.printStackTrace();
            error("分配失败！");
            success(false);
        }
        return end();
    }

    //异步加载数据
    @RequestMapping("asyncLoadData")
    @ResponseBody
    public Object asyncLoadData(Integer roleid) {
        //父
        List<Permission> root = new ArrayList();
        //查询所有的菜单
        List<Permission> childredPermissions = permissionService.queryAllPermissions();
        Map<Integer, Permission> map = new HashMap<Integer, Permission>();
        // /先根据角色id查询改角色之前分配过的权限（许可）
        List<Integer> permissionIdsPermission = permissionService.queryPermissionByRoleId(roleid);
        for (Permission innerPermission : childredPermissions) {
            map.put(innerPermission.getId(), innerPermission);
            //全部菜单许可和该角色之前分配的权限（许可）包含就勾选
            System.out.println(innerPermission.getId());
            if (permissionIdsPermission.contains(innerPermission.getId())) {
                innerPermission.setChecked(true);
            }
        }
        for (Permission childredPermission : childredPermissions) {
            //将循环遍历的菜单设置为子菜单
            Permission child = childredPermission;
            //判断该菜单的pid是否为空如果为空则是父菜单
            if (child.getPid() == null) {
                root.add(child);
            } else {
                //之后通过获取他的父类id找到他的父类菜单
                //之后把资料菜单添加进去
                Permission parent = map.get(child.getPid());
                parent.getChildren().add(child);

            }

        }


        return root;
    }


}
