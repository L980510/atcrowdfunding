package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Type;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

public interface TypeService {
    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    Type selectByPrimaryKey(Integer id);

    List<Type> selectAll();

    int updateByPrimaryKey(Type record);

    
    /**
     * 高级查询（分页和条件查询）
     * @param map
     * @return
     */
    PageResult queryTypePageData(Map map);

    //int toBatchDelete(Integer[] ids);

   int toBatchDeleteType(DateVo datas);



}
