package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Cert;

import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper接口
 */
public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);


    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
   // List<Cert> queryList(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
    //先查询总记录数

    //Integer queryCountCert();
    //在查询每一页的数据
    //因为mybatis不支持传入多个参数，如果需要传入多个参数需要使用到@Param
    List<Cert> queryList(Map map);
    //先查询总记录数

    Integer queryCountCert(Map map);
    //批量删除
   //int toBatchDelete(Integer[] ids);

   int toBatchDeleteCert(DateVo datas);

}