package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

public interface AdvertisementService {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    
    /**
     * 高级查询（分页和条件查询）
     * @param map
     * @return
     */
    PageResult queryAdvertisementPageData(Map map);

    //int toBatchDelete(Integer[] ids);

   int toBatchDeleteAdvertisement(DateVo datas);



}
