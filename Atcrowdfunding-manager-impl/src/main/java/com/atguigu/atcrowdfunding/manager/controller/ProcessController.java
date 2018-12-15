package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.PageResult;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.mail.Multipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程定义控制层
 */
@Controller
@RequestMapping("/process")
public class ProcessController {
    //从容器注入服务对象
    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("tolist")
    public String index(){
        return "process/index";
    }

    /**
     * 查询流程定义对象
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Object list(@RequestParam(value ="currentPage",required = false,defaultValue = "1") Integer currentPage,@RequestParam(value ="pageSize",required = false,defaultValue = "10") Integer pageSize){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //查询流程定义数据
            ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();

            List<ProcessDefinition> processDefinitions = query.listPage((currentPage-1)*pageSize, pageSize);
            //防止重复对用出现死循环
            //	//查询流程定义集合数据,可能出现了自关联,导致Jackson组件无法将集合序列化为JSON串.
            List<Map<String, Object>> pidMap = new ArrayList<Map<String, Object>>();
            //使用Map来代替流程定义对象
            for (ProcessDefinition processDefinition : processDefinitions) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id",processDefinition.getId());
                map.put("name",processDefinition.getName());
                map.put("version",processDefinition.getVersion());
                map.put("key",processDefinition.getKey());

                pidMap.add(map);
            }

            PageResult pageResult = new PageResult(currentPage, pageSize);
            Long totalcount = query.count();
            //因为流程框架帮我们生成了他们的实现类等只需要我们把一下参数设置进去
            //因为分页数据中有两查需要查询数据库之后提供给页面走显示
            pageResult.setTotalCount(totalcount.intValue());
            pageResult.setDatas(pidMap);
            ajaxResult.setPage(pageResult);
            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("流程定义查询失败！");
        }

        return ajaxResult;
    }
    /**
     * 部署流程定义对象
     * @return
     */
    @RequestMapping("deploy")
    @ResponseBody
    public Object deploy(HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取上传文件上传对象
            MultipartHttpServletRequest multipartHttpServletRequest =
                    (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("processDefFile");
            //创建部署对象但是你要告诉他你要部署对象在哪里，之后再镜像部署
            repositoryService.createDeployment()
                    .addInputStream(multipartFile.getOriginalFilename(),multipartFile.getInputStream())
                    .deploy();
            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("流程定义部署失败！");
        }

        return ajaxResult;
    }
    //删除流程定义对象
    @RequestMapping("toDelete")
    @ResponseBody
    public Object toDelete(String id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
             //流程定义id---》流程定义对象---》部署id
            //查询流程定义
            ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
            ProcessDefinition processDefinition = query.processDefinitionId(id).singleResult();
            String deploymentId = processDefinition.getDeploymentId();
            //删除流程定义（级联）
            repositoryService.deleteDeployment(deploymentId,true);
            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("流程定义对象删除失败！");
        }

        return ajaxResult;
    }

    //展示流程定义对象图片（你需要拿到该流程定义对象id之后再将他查询出来）
    @RequestMapping("toShow")
    @ResponseBody
    public Object show(String id,Map map){
        map.put("id",id);
        return "process/doShow";
    }
    @RequestMapping("/doShow")
    @ResponseBody
    public Object doShow(String id, HttpServletResponse response){
        AjaxResult ajaxResult = new AjaxResult();
        try {
             //流程定义id---》流程定义对象---》部署id
            //查询流程定义
            ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
            ProcessDefinition processDefinition = query.processDefinitionId(id).singleResult();
            InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(resourceAsStream,outputStream);


            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("展示流程定义对象失败！");
        }

        return ajaxResult;
    }
}
