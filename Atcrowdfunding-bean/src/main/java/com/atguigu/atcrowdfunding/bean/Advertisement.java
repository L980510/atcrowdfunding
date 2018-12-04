package com.atguigu.atcrowdfunding.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 广告实体类
 */
@Setter@Getter
@AllArgsConstructor@NoArgsConstructor
public class Advertisement {
    //主键
    private Integer id;
    //广告name
    private String name;
    //图片
    private String iconpath;
    //状态：0 - 草稿， 1 - 未审核， 2 - 审核完成， 3 - 发布
    private String status;
    //链接地址
    private String url;
    //上传广告的用户
    private Integer userid;
}
