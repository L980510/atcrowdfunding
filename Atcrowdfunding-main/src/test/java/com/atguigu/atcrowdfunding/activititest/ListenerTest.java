package com.atguigu.atcrowdfunding.activititest;

import com.atguigu.atcrowdfunding.activiti.listener.NoListener;
import com.atguigu.atcrowdfunding.activiti.listener.YesListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListenerTest {
   //写在流程测试里了
   /* public static void main(String[] args) {
        //部署流程
        Map<String, Object> map = new HashMap<String, Object>();
        YesListener yesListener = new YesListener();
        NoListener noListener = new NoListener();
        map.put("yesListener",yesListener);
        map.put("noListener",noListener);
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring/spring-*.xml");
        ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
        //通过流程引擎对象获取运行服务对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //通过运行时期对象来启动实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1", map);
        //之后通过引擎休息获取任务服务
        TaskService taskService = processEngine.getTaskService();
        //通过任务服务对象查询该部署并且委托给谁
        List<Task> list =
                taskService.createTaskQuery().taskAssignee("zhangsan").list();
        System.out.println("zhangsan任务数量："+list.size());
        for (Task task : list) {
            System.out.println("id"+task.getId());
            System.out.println("name"+task.getName());
            //领取任务并且完成任务
           // taskService.complete(task.getId(),"flg",false);
        }
        System.out.println("流程结束");
    }*/
}
