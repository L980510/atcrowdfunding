package com.atguigu.atcrowdfunding.manager.service.impl;




import com.atguigu.atcrowdfunding.manager.dao.TestMapper;
import com.atguigu.atcrowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImplment implements TestService {
    @Autowired
    private TestMapper testDao;

    public void insert() {
        Map map = new HashMap();
        map.put("username", "哈哈");
        testDao.insertMapper(map);
    }
}
