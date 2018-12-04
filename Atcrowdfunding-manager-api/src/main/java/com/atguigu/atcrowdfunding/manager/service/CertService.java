package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;

import java.util.List;
import java.util.Map;

public interface CertService {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);

    
    /**
     * 高级查询（分页和条件查询）
     * @param map
     * @return
     */
    PageResult queryCertPageData(Map map);

    //int toBatchDelete(Integer[] ids);

   int toBatchDeleteCert(DateVo datas);



}
