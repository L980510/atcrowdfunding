package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Cert;

import com.atguigu.atcrowdfunding.manager.dao.CertMapper;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CertServiceImpl implements CertService {
    @Autowired
    private CertMapper certMapper;
    public int deleteByPrimaryKey(Integer id) {
        return certMapper.deleteByPrimaryKey(id);
    }

    public int insert(Cert record) {
        return certMapper.insert(record);
    }

    public Cert selectByPrimaryKey(Integer id) {
        return certMapper.selectByPrimaryKey(id) ;
    }

    public List<Cert> selectAll() {
        return certMapper.selectAll();
    }

    public int updateByPrimaryKey(Cert record) {
        return certMapper.updateByPrimaryKey(record);
    }

    public PageResult queryCertPageData(Map map) {
        PageResult pageResult=new PageResult((Integer) map.get("currentPage"),(Integer)map.get("pageSize"));
        //两查（查询总记录数，和每一页显示的数据）
        int totalCount=certMapper.queryCountCert(map);
         //因为查询每一页显示的数据需要其实页
        Integer startIndex = pageResult.getStartIndex();
        map.put("startIndex",startIndex);
        List<Cert> datas=certMapper.queryList(map);
        pageResult.setTotalCount(totalCount);
        pageResult.setDatas(datas);
        return pageResult;
    }
    public int toBatchDeleteCert(DateVo datas) {
        int totalCount=certMapper.toBatchDeleteCert(datas);
        if (totalCount!=datas.getCertList().size()) {
            throw new RuntimeException("批量删除失败！");
        }
        return totalCount;
    }



}
