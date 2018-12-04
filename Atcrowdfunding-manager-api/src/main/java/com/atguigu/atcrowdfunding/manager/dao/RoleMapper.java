package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper接口
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);


    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
   // List<Role> queryList(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
    //先查询总记录数

    //Integer queryCountRole();
    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
    List<Role> queryList(Map map);
    //先查询总记录数

    Integer queryCountRole(Map map);
    //批量删除
   //int toBatchDelete(Integer[] ids);

   int toBatchDeleteRole(DateVo datas);
    //为角色分配权限
    int insertRolePermission(RolePermission rolePermission);
    //为角色分配权限之前将之前角色的权限删除在进行添加到数据库中
    void deleteRolePermissionByRoleid(Integer roleid);
}