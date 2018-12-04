package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

public interface RoleService {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    
    /**
     * 高级查询（分页和条件查询）
     * @param map
     * @return
     */
    PageResult queryRolePageData(Map map);

    //int toBatchDelete(Integer[] ids);

    int toBatchDeleteRole(DateVo datas);


    //为角色分配权限
    Integer saveAssignPermission(Integer roleid, DateVo datas);
}
