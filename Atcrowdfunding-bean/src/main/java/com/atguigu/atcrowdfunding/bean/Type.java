package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 项目分类实体
 */
@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class Type {
    //主键
    private Integer id;
    //项目分类名称
    private String name;
    //项目备注
    private String remark;
}
