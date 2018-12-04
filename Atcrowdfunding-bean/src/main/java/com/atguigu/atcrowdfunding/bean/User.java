package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *用户实体类
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
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
  //创建时间
  private String createTime;

}


