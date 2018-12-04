package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Type;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper接口
 */
public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    Type selectByPrimaryKey(Integer id);

    List<Type> selectAll();

    int updateByPrimaryKey(Type record);


    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
   // List<Type> queryList(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
    //先查询总记录数

    //Integer queryCountType();
    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
    List<Type> queryList(Map map);
    //先查询总记录数

    Integer queryCountType(Map map);
    //批量删除
   //int toBatchDelete(Integer[] ids);

   int toBatchDeleteType(DateVo datas);

}