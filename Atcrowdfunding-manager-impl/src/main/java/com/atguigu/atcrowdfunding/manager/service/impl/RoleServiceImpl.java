package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id) ;
    }

    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    public int updateByPrimaryKey(Role record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    public PageResult queryRolePageData(Map map) {
        PageResult pageResult=new PageResult((Integer) map.get("currentPage"),(Integer)map.get("pageSize"));
        //两查（查询总记录数，和每一页显示的数据）
        int totalCount=roleMapper.queryCountRole(map);
         //因为查询每一页显示的数据需要其实页
        Integer startIndex = pageResult.getStartIndex();
        map.put("startIndex",startIndex);
        List<Role> datas=roleMapper.queryList(map);
        pageResult.setTotalCount(totalCount);
        pageResult.setDatas(datas);
        return pageResult;
    }
    public int toBatchDeleteRole(DateVo datas) {
        int totalCount=roleMapper.toBatchDeleteRole(datas);
        if (totalCount!=datas.getRoleList().size()) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }

    /**
     * 为角色分配权限（注意：需要将之前该角色的权限删除，在把重新分配的权限添加到数据库中
     * 如果不这么做将会添加重复数据）
     * @param roleid  角色id
     * @param datas 权限ids
     * @return
     */
    public Integer saveAssignPermission(Integer roleid, DateVo datas) {
        roleMapper.deleteRolePermissionByRoleid(roleid);
        List<Integer> permissionids = datas.getIds();
        int count=0;
        for (Integer permissionid : permissionids) {
            RolePermission rolePermission=new RolePermission();
            rolePermission.setRoleid(roleid);
            rolePermission.setPermissionid(permissionid);
            count+=roleMapper.insertRolePermission(rolePermission);
        }
        return count;
    }


}
