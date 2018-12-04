package com.atguigu.atcrowdfunding.controller;
//改善控制器（handler）代码

import java.util.HashMap;
import java.util.Map;

/**
 * 封装父类代码
 */
public class BaseController {
    //通过ThreadLocal来共享数据
    private ThreadLocal<Map<String,Object>>datas=new ThreadLocal<Map<String, Object>>();

    //开始方法
    public void start(){
        Map<String, Object> resultMap = new HashMap();
        datas.set(resultMap);
    }
    //结束方法
    public Object end(){
        Map<String, Object> resultMap = new HashMap();
        datas.remove();
        return resultMap;
    }
    //成功方法（给客服端返回的结果）
    public void  success(boolean flg){
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("success",flg);
    }
    //参数(用于共享数据到页面的方法)
    public void param(String key,String value){
        Map<String, Object> resultMap = datas.get();
        resultMap.put(key, value);

    }
    //错误（当访问遇到错误时候给客服端返回结果）
    public void error(String msg){
        Map<String, Object> resultMap = datas.get();
        resultMap.put("message",msg);
    }






}
