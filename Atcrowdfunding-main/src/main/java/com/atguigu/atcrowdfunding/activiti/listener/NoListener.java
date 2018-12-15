package com.atguigu.atcrowdfunding.activiti.listener;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.beans.ExceptionListener;

/**
 * 流程监听器
 */
public class NoListener implements ExecutionListener {

    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("流程拒绝");
    }


}
