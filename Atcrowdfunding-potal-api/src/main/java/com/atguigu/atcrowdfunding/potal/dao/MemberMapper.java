package com.atguigu.atcrowdfunding.potal.dao;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.User;

import java.util.List;
import java.util.Map;

/**
 * 会员Mapper接口
 */
public interface MemberMapper {


    int insert(Map map);

    Member selectByLoginAcct(String loginAcct);



}