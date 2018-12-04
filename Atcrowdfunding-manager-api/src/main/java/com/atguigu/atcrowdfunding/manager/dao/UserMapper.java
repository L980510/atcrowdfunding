package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;

/**
 * 用户Mapper接口
 */
public interface UserMapper {
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
    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
   // List<User> queryList(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
    //先查询总记录数

    //Integer queryCountUser();
    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
    List<User> queryList(Map map);
    //先查询总记录数

    Integer queryCountUser(Map map);
    //批量删除
    int toBatchDelete(Integer[] ids);

    int toBatchDeleteUser(DateVo datas);
    ////通过用户id查询对应用户在数据库中有哪些角色
    List<Integer> selectByUserIdRoleData(Integer id);

    //通过用户id添加角色信息
    int saveUserRoleRelation(@Param("userid") Integer id,@Param("roleid") Integer roleid);
    //通过用户id取消用户某些角色
    int deleteUserRoleRelation(@Param("userid") Integer userid,@Param("ids") List<Integer> roleid);
}