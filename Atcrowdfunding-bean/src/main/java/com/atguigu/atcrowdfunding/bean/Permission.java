package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限实体
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    //主键
    private Integer id;
    //父类id
    private Integer pid;
    //名称
    private String name;
    //图标
    private String icon;
    //链接地址
    private String url;

    private boolean open=true;
    //复选框是否勾选
    private boolean checked;
    //
    private int level;
    //设置关联关系(子菜单和父菜单关系是多对一关系)
    private List<Permission> children =new ArrayList<Permission>();
}
