package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 资质维护实体类
 */
@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class Cert {
    //主键
    private Integer id;
    //项目名称
    private String name;
}
