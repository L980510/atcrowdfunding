package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限Service
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper premissionMapper;

    /**
     * 通过父类id查询子类菜单
     * @param id 父类id
     * @return
     */
    public List<Permission> queryChildrenByRootIdPermission(Integer id) {
        return premissionMapper.queryChildrenByRootIdPermission(id);
    }

    /**
     * 查询父类菜单
     * @return  父类菜单对象
     */
    public Permission queryRootPermission() {
        return premissionMapper.queryRootPermission();
    }

    /**
     * 一次性把所有的数据全部查询出来
     * @return
     */
    public List<Permission> queryAllPermissions() {
        return premissionMapper.queryAllPermissions();
    }

    public int savePermission(Permission permission) {
        return premissionMapper.insert(permission);
    }

    public Permission queryPermissionById(Integer id) {
        return premissionMapper.selectByPrimaryKey(id);
    }

    public int updatePermission(Permission permission) {
        return premissionMapper.updateByPrimaryKey(permission);
    }

    public int delectPermission(Integer id) {
        return premissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过角色id查询之前分配过的权限
     * @param roleid
     * @return
     */
        public List<Integer> queryPermissionByRoleId(Integer roleid) {
        return premissionMapper.queryPermissionByRoleId(roleid);
    }

    /**
     * 通过用户id查询对应登入许可
     * @param userid 用户id
     * @return
     */
    public List<Permission> getPermissionByUserId(Integer userid) {
        return premissionMapper.getPermissionByUserId(userid);
    }
}
