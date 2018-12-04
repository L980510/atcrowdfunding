package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.excaption.LoginUserException;
import com.atguigu.atcrowdfunding.potal.dao.MemberMapper;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 会员业务层实现类
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;


    /**
     * 如果没有账号需要注册账号
     * @param
     * @return
     */
    public int memberRegistered(Map<String, String> map) {
        //注册逻辑
        //1先判断给用户名是否注册
        String loginAcct=map.get("loginAcct");
        Member member = memberMapper.selectByLoginAcct(loginAcct);
        //如果如果有就提示用户给用户名已经注册了
        //如果注册数据为空也要提示用户
        if (member!=null) {
           throw  new LoginUserException("账号已经存在了!亲重新输入");
        }
        //2保存的时候需要先将密码加密在保存
        String userPswd = map.get("userPswd");

        String acctType=map.get("acctType");
        String digestuserPswd = MD5Util.digest(userPswd);
        map.put("username",loginAcct);
        map.put("userPswd",digestuserPswd);
        map.put("userType",acctType);
        map.put("authStatus","0");
        //最后将数据保存到数据库
        int insert = memberMapper.insert(map);

        return insert;
    }
}
