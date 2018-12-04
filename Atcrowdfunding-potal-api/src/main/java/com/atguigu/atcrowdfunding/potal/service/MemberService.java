package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Member;

import java.util.Map;

/**
 * \会员业务层
 */
public interface MemberService {

    int memberRegistered(Map<String, String> map);
}
