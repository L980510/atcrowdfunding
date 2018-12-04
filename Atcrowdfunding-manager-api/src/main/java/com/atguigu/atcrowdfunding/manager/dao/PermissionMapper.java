package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

/**
 * 权限Mapper接口
 */
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
    List<Permission> queryList(Map map);
    //先查询总记录数

    Integer queryCountPermission(Map map);
    //查询父类菜单
    Permission queryRootPermission();
    //通过父类菜单id查询子类菜单
    List<Permission> queryChildrenByRootIdPermission(Integer id);
    //将所有的菜单查询出来
    List<Permission> queryAllPermissions();
    //通过角色id查询权限
    List<Integer> queryPermissionByRoleId(Integer roleid);
   //通过用户id查询对应登入许可
    List<Permission> getPermissionByUserId(Integer userid);
    //批量删除
   //int toBatchDelete(Integer[] ids);

   //int toBatchDeletePermission(DateVo datas);

}