package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 会员实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    //主键
    private Integer id;
    //登入的账号
    private String loginAcct;
    //登入的密码
    private String userPswd;
    //用户名称
    private String username;
    //电子邮箱
    private String email;
    //实名认证状态
    // 实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证',
    private String authStatus;
    //用户类型
    private String userType;
    //真实名称
    private String realName;
    //身份证号码
    private String cardNum;
    //账号类型
    //'0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府',
    private String acctType;
    

}