package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Param;
import com.atguigu.atcrowdfunding.manager.dao.ParamMapper;
import com.atguigu.atcrowdfunding.manager.service.ParamService;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class ParamServiceImpl implements ParamService {
    @Autowired
    private ParamMapper paramMapper;
    public int deleteByPrimaryKey(Integer id) {
        return paramMapper.deleteByPrimaryKey(id);
    }

    public int insert(Param record) {
        return paramMapper.insert(record);
    }

    public Param selectByPrimaryKey(Integer id) {
        return paramMapper.selectByPrimaryKey(id) ;
    }

    public List<Param> selectAll() {
        return paramMapper.selectAll();
    }

    public int updateByPrimaryKey(Param record) {
        return paramMapper.updateByPrimaryKey(record);
    }

    public PageResult queryParamPageData(Map map) {
        PageResult pageResult=new PageResult((Integer) map.get("currentPage"),(Integer)map.get("pageSize"));
        //两查（查询总记录数，和每一页显示的数据）
        int totalCount=paramMapper.queryCountParam(map);
         //因为查询每一页显示的数据需要其实页
        Integer startIndex = pageResult.getStartIndex();
        map.put("startIndex",startIndex);
        List<Param> datas=paramMapper.queryList(map);
        pageResult.setTotalCount(totalCount);
        pageResult.setDatas(datas);
        return pageResult;
    }
    public int toBatchDeleteParam(DateVo datas) {
        int totalCount=paramMapper.toBatchDeleteParam(datas);
        if (totalCount!=datas.getParamList().size()) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }



}
