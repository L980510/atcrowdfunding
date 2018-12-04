package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.excaption.LoginUserException;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户业务层的实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insert(User user) {
        //因为页面只传了3个值，
        // 需要将时间和和其他的值一起设置进来并且将密码设置进来
        user.setUserPswd(Const.PASS_WORD);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreateTime(dateFormat.format(new Date()));
        return userMapper.insert(user);
    }

    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    /**
     * 对登入用户进行查询是否给用户是否存在，如果不存在，提示用户，如果页面输入的
     * 账户密码和查询的账号密码不一致提示用户
     * @param paramMap
     * @return
     */
    public User queryUserlogin(Map<String, Object> paramMap) {
        //拿到页面封装的数据---》调用持久层方法进行查询页面的账号密码是否正确
        User user = userMapper.queryUserlogin(paramMap);
        //如果为空提示用户账号密码不能为空和账户密码不正确也要提示用户
        if (user==null) {
            throw new LoginUserException("账号密码不能为空");
        }
        //如果正确就将查询到的结果返回到控制层
        return user;
    }

    public PageResult queryPageUser(Map map) {
        Integer pageSize = (Integer) map.get("pageSize");
        PageResult pageResult = new PageResult((Integer) map.get("currentPage"), pageSize);
        Integer startIndex = pageResult.getStartIndex();
        map.put("startIndex",startIndex);
        //先查询总记录数
        //Integer  count=userMapper.queryCountUser();
        Integer count=userMapper.queryCountUser(map);
        //在查询每一页的数据
       // List<User> datas=userMapper.queryList(startIndex,pageSize);
        List<User> datas=userMapper.queryList(map);
        pageResult.setTotalCount(count);
        pageResult.setDatas(datas);
        //System.out.println("PageResult:"+pageResult);
        return pageResult;
    }
    //批量删除
    public int toBatchDelete(Integer[] ids) {
        int totalCount=userMapper.toBatchDelete(ids);
        if (totalCount!=ids.length) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }

    public int toBatchDeleteUser(DateVo datas) {
        int totalCount=userMapper.toBatchDeleteUser(datas);
        if (totalCount!=datas.getUserList().size()) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }

    /**
     *  //通过用户id查询对应用户在数据库中有哪些角色
     * @param id
     * @return
     */
    public List<Integer> selectByUserIdRoleData(Integer id) {

        return userMapper.selectByUserIdRoleData(id);
    }

    public int saveUserRoleRelation(Integer id, DateVo data) {
        int count=0;
        //添加该用户的分配的角色
        List<Integer> ids = data.getIds();
        for (Integer roleid : ids) {
            int saveUserRole=userMapper.saveUserRoleRelation(id,roleid);
            count+=saveUserRole;
        }

        return count;
    }

    //通过用户id取消用户某些角色
    public int deleteUserRoleRelation(Integer userid, DateVo data) {

        List<Integer> ids = data.getIds();
        int deleteUserRole = userMapper.deleteUserRoleRelation(userid, ids);

        return deleteUserRole;
    }

    /**
     * 分页查询用户管理
     * @param 、、pageResult  页面封装数据
     * @return
     */
    /*public PageResult queryPageUser(PageResult pageResult) {

        Integer startIndex = pageResult.getStartIndex();
        //先查询总记录数
       Integer  count=userMapper.queryCountUser();
       //在查询每一页的数据
        List<User> datas=userMapper.queryList(startIndex,pageResult.getPageSize());
       pageResult.setTotalCount(count);
       pageResult.setDatas(datas);
        //System.out.println("PageResult:"+pageResult);
        return pageResult; }*/

}
