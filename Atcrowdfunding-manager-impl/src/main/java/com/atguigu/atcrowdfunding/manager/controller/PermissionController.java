package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController{

    @Autowired
    private PermissionService permissionService;

   @RequestMapping("tolist")
    public String toIndex(){
       return "permission/index";
   }
    @RequestMapping("toAdd")
    public String toAdd(){
        return "permission/toAdd";
    }

    /**
     * 添加菜单
     * @return
     */
    @RequestMapping("doAdd")
    @ResponseBody
    public Object doAdd(Permission permission) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count=permissionService.savePermission(permission);
            if (count!=0) {
                ajaxResult.setSuccess(true);

            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage("添加菜单失败");
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }
    @RequestMapping("toUpdate")
    public String toUpdate(Integer id,Map map){
        Permission permission=permissionService.queryPermissionById(id);
        map.put("permission",permission);
        return "permission/toUpdate";
    }

    /**
     * 修改菜单
     * @return
     */
    @RequestMapping("doUpdate")
    @ResponseBody
    public Object toUpdate(Permission permission) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count=permissionService.updatePermission(permission);
            if (count!=0) {
                ajaxResult.setSuccess(true);

            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage("修改菜单失败");
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    /**
     * 删除菜单
     * @return
     */
    @RequestMapping("doDelete")
    @ResponseBody
    public Object doDelect(Integer id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count=permissionService.delectPermission(id);
            if (count!=0) {
                ajaxResult.setSuccess(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除菜单失败");
        }
        return ajaxResult;
    }

    /**
     * 重构删除菜单接口
     * @param id
     * @return
     */
   /* @RequestMapping("doDelete")
    @ResponseBody
    public Object doDelect(Integer id) {
       start();
        try {
            int count=permissionService.delectPermission(id);
            if (count!=0) {
               success(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
          success(false);
          error("删除失败");
        }
        return end();
    }*/

    /**
     * 加载数据
     * @return 查询数据库加载数据demo4 为了解决查询过多次数据库
     */
    @RequestMapping("loadData")
    @ResponseBody
    public Object loadData() {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            ArrayList<Permission> root = new ArrayList();
            //一次性查询出所有数据
            List<Permission> childrenPermissions = permissionService.queryAllPermissions();
            Map<Integer, Permission> map = new HashMap<Integer, Permission>();
            for (Permission innerPermission : childrenPermissions) {
                map.put(innerPermission.getId(), innerPermission);
            }
            for (Permission permission : childrenPermissions) {
                //通过子菜单查询父菜单
                //子菜单
                //假设为子菜单
                Permission child = permission;
                //并且判断该子菜单的pid是否为null，如果为null就为父菜单
                if (child.getPid() == null) {
                    //如果是就设置为父菜单
                    root.add(permission);
                } else {
                    Permission parent = map.get(child.getPid());
                    parent.getChildren().add(child);

                }
            }

            ajaxResult.setSuccess(true);
            ajaxResult.setData(root);
            // 需要的是下面这种方式
            //{"success":true,"message":null,"page":null,
            // "data":[{"id":null,"pid":null,"name":"系统管理","icon":null,"url":null,"open":true,
            // "children":[{"id":null,"pid":null,"name":"角色管理","icon":null,"url":null,"open":false,
            // "children":[]},{"id":null,"pid":null,"name":"权限管理","icon":null,"url":null,"open":false,
            // "children":[]}]}]}

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage("菜单加载失败");
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }

    /**
     * 加载数据
     * @return 查询数据库加载数据demo3 为了解决查询过多次数据库
     */
  /*  @RequestMapping("loadData")
    @ResponseBody
    public Object loadData() {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //一次性查询出所有数据
            List<Permission> childrenPermissions = permissionService.queryAllPermissions();
            for (Permission permission : childrenPermissions) {
                //通过子菜单查询父菜单
                //子菜单
                //假设为子菜单
                Permission child=permission;
                //并且判断该子菜单的pid是否为null，如果为null就为父菜单
                if (child.getPid()==null) {
                    //如果是就设置为父菜单
                    ajaxResult.setData(child);
                }else{
                    //父菜单
                    for (Permission innerChildPermission : childrenPermissions) {
                        //将拿到的所有菜单进行循环遍历处理并且判断子菜单的pid与循环遍历出来的
                        //菜单是否相等如果相等就为该子类菜单的子类菜单
                        if (child.getPid()==innerChildPermission.getId()) {
                            innerChildPermission.setOpen(true);
                            Permission parent=innerChildPermission;
                            parent.getChildren().add(child);
                            break;//跳出内循环体 如果跳出外层循环,需要使用标签跳出

                        }
                    }
                }
            }
            ajaxResult.setSuccess(true);
            //ajaxResult.setData(childrenPermissions);
            // 需要的是下面这种方式
            //{"success":true,"message":null,"page":null,
            // "data":[{"id":null,"pid":null,"name":"系统管理","icon":null,"url":null,"open":true,
            // "children":[{"id":null,"pid":null,"name":"角色管理","icon":null,"url":null,"open":false,
            // "children":[]},{"id":null,"pid":null,"name":"权限管理","icon":null,"url":null,"open":false,
            // "children":[]}]}]}

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }*/

    /**
     * 加载数据
     * @return 查询数据库加载数据demo2  效率低.查询的数据库次数太多,可以采用一次SQL查询出所有数据.
     */
    /*@RequestMapping("loadData")
    @ResponseBody
    public Object loadData() {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //父
            Permission root = permissionService.queryRootPermission();
            //设置父菜单默认打开
            root.setOpen(true);
            //子
            List<Permission> children = permissionService.queryChildrenByRootIdPermission(root.getId());
            for (Permission childrenPermission: children) {
                childrenPermission.setOpen(true);
                List<Permission> innerChildren = permissionService.queryChildrenByRootIdPermission(childrenPermission.getId());
                childrenPermission.setChildren(innerChildren);
            }
            root.setChildren(children);
            ajaxResult.setSuccess(true);
            ajaxResult.setData(root);
            // 需要的是下面这种方式
            //{"success":true,"message":null,"page":null,
            // "data":[{"id":null,"pid":null,"name":"系统管理","icon":null,"url":null,"open":true,
            // "children":[{"id":null,"pid":null,"name":"角色管理","icon":null,"url":null,"open":false,
            // "children":[]},{"id":null,"pid":null,"name":"权限管理","icon":null,"url":null,"open":false,
            // "children":[]}]}]}

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }*/

    /**
     * 加载数据
     * @return 查询数据库加载数据demo1
     */
    /*@RequestMapping("loadData")
    @ResponseBody
    public Object loadData() {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //父
            Permission root = permissionService.queryRootPermission();
            //设置父菜单默认打开
            root.setOpen(true);
            //子
            List<Permission> children = permissionService.queryChildrenByRootIdPermission(root.getId());
            for (Permission childrenPermission: children) {
                childrenPermission.setOpen(true);
                List<Permission> innerChildren = permissionService.queryChildrenByRootIdPermission(childrenPermission.getId());
                childrenPermission.setChildren(innerChildren);
            }
            root.setChildren(children);
            ajaxResult.setSuccess(true);
            ajaxResult.setData(root);
            // 需要的是下面这种方式
            //{"success":true,"message":null,"page":null,
            // "data":[{"id":null,"pid":null,"name":"系统管理","icon":null,"url":null,"open":true,
            // "children":[{"id":null,"pid":null,"name":"角色管理","icon":null,"url":null,"open":false,
            // "children":[]},{"id":null,"pid":null,"name":"权限管理","icon":null,"url":null,"open":false,
            // "children":[]}]}]}

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }*/
    /**
     * 加载数据
     * @return 模拟加载数据库数据demo
     */
   /* @RequestMapping("loadData")
    @ResponseBody
    public Object loadData() {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //父
            List<Permission> root = new ArrayList();
            Permission permission =
                    new Permission();
            permission.setName("系统管理");
            permission.setOpen(true);
            root.add(permission);
            List<Permission> children = new ArrayList();
            Permission permission2 =
                    new Permission();
            permission2.setName("权限管理");
            Permission permission1 =
                    new Permission();
            permission1.setName("角色管理");
            children.add(permission1);
            children.add(permission2);
            permission.setChildren(children);
            ajaxResult.setSuccess(true);
            ajaxResult.setData(root);
           // {"success":true,"message":null,"page":null,
            // "data":{"id":null,"pid":null,"name":"系统管理","icon":null,"url":null,"open":true,
            // "children":[{"id":null,"pid":null,"name":"角色管理","icon":null,"url":null,"open":false,
            // "children":[]},{"id":null,"pid":null,"name":"权限管理","icon":null,"url":null,"open":false,
            // "children":[]}]}}-------》需要的是下面这种方式
            //{"success":true,"message":null,"page":null,
            // "data":[{"id":null,"pid":null,"name":"系统管理","icon":null,"url":null,"open":true,
            // "children":[{"id":null,"pid":null,"name":"角色管理","icon":null,"url":null,"open":false,
            // "children":[]},{"id":null,"pid":null,"name":"权限管理","icon":null,"url":null,"open":false,
            // "children":[]}]}]}

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
            ajaxResult.setSuccess(false);
        }
        return ajaxResult;
    }*/
}
