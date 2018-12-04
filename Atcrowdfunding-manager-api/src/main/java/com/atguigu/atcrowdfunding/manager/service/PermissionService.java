package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {
    //通过父类菜单id查询子类菜单
    List<Permission> queryChildrenByRootIdPermission(Integer id);
    //查询父类查询（父类的pid是没有值的 null）
    Permission queryRootPermission();
    //一次性把所有的数据全部查询出来
    List<Permission> queryAllPermissions();
    //添加菜单
    int savePermission(Permission permission);
    //通过id查询对应许可对象
    Permission queryPermissionById(Integer id);
   //修改菜单
    int updatePermission(Permission permission);
    //删除菜单
    int delectPermission(Integer id);
    //通过角色id查询对应权限id
    List<Integer> queryPermissionByRoleId(Integer roleid);
    //通过用户查询对应权限
    List<Permission> getPermissionByUserId(Integer userid);
}
