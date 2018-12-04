package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Type;
import com.atguigu.atcrowdfunding.manager.dao.TypeMapper;
import com.atguigu.atcrowdfunding.manager.service.TypeService;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    public int deleteByPrimaryKey(Integer id) {
        return typeMapper.deleteByPrimaryKey(id);
    }

    public int insert(Type record) {
        return typeMapper.insert(record);
    }

    public Type selectByPrimaryKey(Integer id) {
        return typeMapper.selectByPrimaryKey(id) ;
    }

    public List<Type> selectAll() {
        return typeMapper.selectAll();
    }

    public int updateByPrimaryKey(Type record) {
        return typeMapper.updateByPrimaryKey(record);
    }

    public PageResult queryTypePageData(Map map) {
        PageResult pageResult=new PageResult((Integer) map.get("currentPage"),(Integer)map.get("pageSize"));
        //两查（查询总记录数，和每一页显示的数据）
        int totalCount=typeMapper.queryCountType(map);
         //因为查询每一页显示的数据需要其实页
        Integer startIndex = pageResult.getStartIndex();
        map.put("startIndex",startIndex);
        List<Type> datas=typeMapper.queryList(map);
        pageResult.setTotalCount(totalCount);
        pageResult.setDatas(datas);
        return pageResult;
    }
    public int toBatchDeleteType(DateVo datas) {
        int totalCount=typeMapper.toBatchDeleteType(datas);
        if (totalCount!=datas.getTypeList().size()) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }



}
