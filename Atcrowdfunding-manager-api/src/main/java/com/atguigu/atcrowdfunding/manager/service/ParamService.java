package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Param;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

public interface ParamService {
    int deleteByPrimaryKey(Integer id);

    int insert(Param record);

    Param selectByPrimaryKey(Integer id);

    List<Param> selectAll();

    int updateByPrimaryKey(Param record);

    
    /**
     * 高级查询（分页和条件查询）
     * @param map
     * @return
     */
    PageResult queryParamPageData(Map map);

    //int toBatchDelete(Integer[] ids);

   int toBatchDeleteParam(DateVo datas);



}
