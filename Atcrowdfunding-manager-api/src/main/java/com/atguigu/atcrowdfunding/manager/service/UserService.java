package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

/**
 * 用户业务层（Service）
 */
public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /***
     * 查询该登入用户是否存在
     * @param paramMap
     * @return
     */
    User queryUserlogin(Map<String, Object> paramMap);

    /***
     * 分页查询用户管理
      * @param //pageResult  页面封装数据
     * @return
     */
    //PageResult queryPageUser(PageResult pageResult);

    /**
     * 高级查询（分页和条件查询）
     * @param map
     * @return
     */
    PageResult queryPageUser(Map map);

    int toBatchDelete(Integer[] ids);

    int toBatchDeleteUser(DateVo datas);

    //通过用户id查询对应用户在数据库中有哪些角色
    List<Integer> selectByUserIdRoleData(Integer id);

    //批量插入角色
    int saveUserRoleRelation(Integer id, DateVo data);
    //通过用户id取消用户某些角色
    int deleteUserRoleRelation(Integer userid, DateVo data);
}
