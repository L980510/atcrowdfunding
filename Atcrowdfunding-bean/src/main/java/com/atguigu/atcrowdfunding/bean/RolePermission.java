package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 中间表（角色和权限）
 */
@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class RolePermission {
    private Integer roleid;
    private Integer permissionid;
}
