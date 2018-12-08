package com.atguigu.atcrowdfunding.activititest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试流程框架是否成功
 */
public class ActivitiTest {

    ApplicationContext ico=new ClassPathXmlApplicationContext("spring/spring-*.xml");
    ProcessEngine processEngine = (ProcessEngine)ico.getBean("processEngine");
    //7领取任务
    @Test
    public void Activitis7(){
        //通过流程引擎对象获取任务服务
        TaskService taskService = processEngine.getTaskService();
        //通过拿到任务服务创建任务对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //为任务对象分配委托人的组
        List<Task> list = taskQuery.taskCandidateGroup("tl").list();
        //之后将分配任务的这个组分配给那个委托人
        long count = taskQuery.taskAssignee("zhangsan").count();
        System.out.println("zhangsan在领取任务之前数量："+count);
        for (Task task : list) {
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
            //领取任务
            taskService.claim(task.getId(),"zhangsan");
        }
         taskQuery = taskService.createTaskQuery();
         count = taskQuery.taskAssignee("zhangsan").count();
        System.out.println("zhangsan在领取任务之后数量："+count);

    }

    //6获取历史服务对象，查看历史流程
    @Test
    public void Activitis6(){
        //通过流程引擎对象来获取其他的另外7个服务对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //查询流程定义
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("myProcess")
                .latestVersion()
                .singleResult();
        HistoryService historyService = processEngine.getHistoryService();
        //之后通过得到历史服务对象查询最后一次流程实例
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = query.processDefinitionId(processDefinition.getId());
        System.out.println("historicProcessInstanceQuery:"+historicProcessInstanceQuery);
    }
    //5创建流程实力
    @Test
    public void Activitis5(){
        //拿到流程对象的最后一次部署定义
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        //拿到任务service
        TaskService taskService = processEngine.getTaskService();
        //之后通过任务service创建任务对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        //之后通过任务service创建任务对象---》通过任务对象拿到任务处理人（也就是谁来处理给任务）
        List<Task> list1= taskQuery.taskAssignee("zhangsan").list();
        List<Task> list2=taskQuery.taskAssignee("lisi").list();
        System.out.println("zhangsan的任务：");
        for (Task task : list1) {
            System.out.println("id:"+task.getId());
            System.out.println("name:"+task.getName());
            //如果lisi需要领取任务则需要zhangsan完成任务和重写查一遍任务处理人
            taskService.complete(task.getId());
        }
        System.out.println("lisi的任务：");
        for (Task task : list2) {
            System.out.println("id:"+task.getId());
            System.out.println("name:"+task.getName());
            taskService.complete(task.getId());
        }
        //==========因为zhangsan任务完成了，它重新查询一下所以zhangsan现在没有任务了。只有lisi有任务====================
        System.out.println("==========================");
         list1= taskQuery.taskAssignee("zhangsan").list();
         list2=taskQuery.taskAssignee("lisi").list();
        System.out.println("zhangsan的任务：");
        for (Task task : list1) {
            System.out.println("id:"+task.getId());
            System.out.println("name:"+task.getName());
            //如果lisi需要领取任务则需要zhangsan完成任务和重写查一遍任务处理人
            taskService.complete(task.getId());
        }
        System.out.println("lisi的任务：");
        for (Task task : list2) {
            System.out.println("id:"+task.getId());
            System.out.println("name:"+task.getName());
            taskService.complete(task.getId());
        }

    }
    //4创建流程实力
    @Test
    public void Activitis4(){
        //拿到流程对象的最后一次部署定义
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        //运行启动流程实例
        	/*
	 ProcessInstance[101]
	 act_hi_procinst : 历史流程实例表
		保存了流程实例的信息
	 act_hi_taskinst : 历史任务实例表
		保存了流程任务的相关信息
	act_hi_actinst:历史节点表
		保存了流程执行的节点顺序
	 act_ru_execution : 运行时流程执行实例表
		保存了当前流程执行的节点数据,流程开始会自动完成,直接执行第一个任务
	 act_ru_task : 运行时任务节点表
		保存当前流程执行的任务数据
	 */

        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId());
        System.out.println("流程实例："+processInstance);


    }
    //4.1创建流程实力
    //流程变量使用
    //如果存在流程变量，那么在启动流程变量实例时候需要给流程变量赋值
    @Test
    public void Activitis(){
        //拿到流程对象的最后一次部署定义
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        //运行启动流程实例
        	/*
	 ProcessInstance[101]
	 act_hi_procinst : 历史流程实例表
		保存了流程实例的信息
	 act_hi_taskinst : 历史任务实例表
		保存了流程任务的相关信息
	act_hi_actinst:历史节点表
		保存了流程执行的节点顺序
	 act_ru_execution : 运行时流程执行实例表
		保存了当前流程执行的节点数据,流程开始会自动完成,直接执行第一个任务
	 act_ru_task : 运行时任务节点表
		保存当前流程执行的任务数据
	 */
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("tl","zhangsan");
        hashMap.put("pm","lisi");
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(), hashMap);
        System.out.println("流程实例："+processInstance);


    }
    //3查询部署流程定义
    @Test
    public void Activitis3(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("id:"+processDefinition.getId());
            System.out.println("name:"+processDefinition.getName());
            System.out.println("key:"+processDefinition.getKey());
            System.out.println("version:"+processDefinition.getVersion());
            System.out.println("============================");

        }
        System.out.println("查询最近一次的部署定义查询出来：=====================");
        ProcessDefinition processDefinition = processDefinitionQuery.latestVersion().singleResult();
        System.out.println("id:"+processDefinition.getId());
        System.out.println("name:"+processDefinition.getName());
        System.out.println("key:"+processDefinition.getKey());
        System.out.println("version:"+processDefinition.getVersion());

        System.out.println("按照版本号进行排序：=======================");
        ProcessDefinitionQuery definitionQuery = processDefinitionQuery.orderByProcessDefinitionVersion().desc();
        //分页的方法查询
        List<ProcessDefinition> processDefinitions = definitionQuery.listPage(0, 2);
        for (ProcessDefinition definition : processDefinitions) {
            System.out.println("id:"+definition.getId());
            System.out.println("name:"+definition.getName());
            System.out.println("key:"+definition.getKey());
            System.out.println("version:"+definition.getVersion());

        }
        System.out.println("=========================================");
        System.out.println("查询次数："+processDefinitionQuery.count());

        System.out.println("根据流程key查询；流程对象定义：==================");
        ProcessDefinition myProcess = processDefinitionQuery.processDefinitionKey("myProcess").latestVersion().singleResult();
        System.out.println("id:"+myProcess.getId());
        System.out.println("name:"+myProcess.getName());
        System.out.println("key:"+myProcess.getKey());
        System.out.println("version:"+myProcess.getVersion());


    }
    //2部署流程定义
    @Test
    public void Activitis2(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess.bpmn").deploy();
       // Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess2.bpmn").deploy();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess3.bpmn").deploy();
        System.out.println("deploy:"+deploy);

    }
    //1创建流程对象，并且创建流程框架的23张表
    @Test
    public void Activitis1(){
        ProcessEngine processEngine = (ProcessEngine)ico.getBean("processEngine");
        System.out.println("ProcessEngine:"+processEngine);

    }
}
