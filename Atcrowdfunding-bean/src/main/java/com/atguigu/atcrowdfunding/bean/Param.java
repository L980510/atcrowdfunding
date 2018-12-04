package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 参数实体类
 */
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Param {

    //主键
    private Integer id;
    //参数名称
    private String name;
    //代码
    private String code;
    //值
    private String val;
}

