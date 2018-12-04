package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper接口
 */
public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);


    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
   // List<Advertisement> queryList(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
    //先查询总记录数

    //Integer queryCountAdvertisement();
    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
    List<Advertisement> queryList(Map map);
    //先查询总记录数

    Integer queryCountAdvertisement(Map map);
    //批量删除
   //int toBatchDelete(Integer[] ids);

   int toBatchDeleteAdvertisement(DateVo datas);

}