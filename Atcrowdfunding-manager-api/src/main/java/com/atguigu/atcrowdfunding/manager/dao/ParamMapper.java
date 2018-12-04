package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Param;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper接口
 */
public interface ParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Param record);

    Param selectByPrimaryKey(Integer id);

    List<Param> selectAll();

    int updateByPrimaryKey(Param record);


    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
   // List<Param> queryList(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
    //先查询总记录数

    //Integer queryCountParam();
    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
    List<Param> queryList(Map map);
    //先查询总记录数

    Integer queryCountParam(Map map);
    //批量删除
   //int toBatchDelete(Integer[] ids);

   int toBatchDeleteParam(DateVo datas);

}