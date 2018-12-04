package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.manager.dao.AdvertisementMapper;
import com.atguigu.atcrowdfunding.manager.service.AdvertisementService;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementMapper advertisementMapper;
    public int deleteByPrimaryKey(Integer id) {
        return advertisementMapper.deleteByPrimaryKey(id);
    }

    public int insert(Advertisement record) {
        return advertisementMapper.insert(record);
    }

    public Advertisement selectByPrimaryKey(Integer id) {
        return advertisementMapper.selectByPrimaryKey(id) ;
    }

    public List<Advertisement> selectAll() {
        return advertisementMapper.selectAll();
    }

    public int updateByPrimaryKey(Advertisement record) {
        return advertisementMapper.updateByPrimaryKey(record);
    }

    public PageResult queryAdvertisementPageData(Map map) {
        PageResult pageResult=new PageResult((Integer) map.get("currentPage"),(Integer)map.get("pageSize"));
        //两查（查询总记录数，和每一页显示的数据）
        int totalCount=advertisementMapper.queryCountAdvertisement(map);
         //因为查询每一页显示的数据需要其实页
        Integer startIndex = pageResult.getStartIndex();
        map.put("startIndex",startIndex);
        List<Advertisement> datas=advertisementMapper.queryList(map);
        pageResult.setTotalCount(totalCount);
        pageResult.setDatas(datas);
        return pageResult;
    }
    public int toBatchDeleteAdvertisement(DateVo datas) {
        int totalCount=advertisementMapper.toBatchDeleteAdvertisement(datas);
        if (totalCount!=datas.getAdvertisementList().size()) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }



}
