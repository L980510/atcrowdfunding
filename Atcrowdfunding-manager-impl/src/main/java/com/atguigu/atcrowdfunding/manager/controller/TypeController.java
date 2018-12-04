package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Type;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.TypeService;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 项目分类控制层
 */
@Controller
@RequestMapping("type")
public class TypeController extends BaseController{

    @Autowired
    private TypeService typeService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 跳转到项目分类首页
     * @return
     */
    @RequestMapping("tolist")
    public String toList(){
        return "type/index";
    }
    /**
     * 异步加载分页数据
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object doList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                         @RequestParam(value = "pageSize" ,defaultValue = "10")Integer pageSize,
                         String queryText,Map map){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            map.put("currentPage",currentPage);
            map.put("pageSize",pageSize);
            //模糊查询
            if (!StringUtils.isEmpty(queryText)) {
                if (queryText.contains("%")) {
                    queryText.replaceAll("%","////%");
                }
                map.put("queryText",queryText);
            }
            //调用业务方法处理请求
            PageResult pageResult=typeService.queryTypePageData(map);
            ajaxResult.setSuccess(true);
            ajaxResult.setPage(pageResult);
        }catch (Exception e){
            e.printStackTrace();
          ajaxResult.setSuccess(false);
          ajaxResult.setMessage(e.getMessage());
        }
        //将处理好的数据共享到页面进行显示
       return ajaxResult;

    }

    //添加项目分类
    @RequestMapping("add")
    public String toAdd(){
        return "type/toAdd";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Type type){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            int insert = typeService.insert(type);
            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("添加失败");

        }
        //将处理好的数据共享到页面进行显示
        return ajaxResult;

    }
    //修改
    @RequestMapping("update")
    public String update(Integer id,Map map){
        Type type = typeService.selectByPrimaryKey(id);
        map.put("type",type);
        return "type/toUpdate";
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Object doUpdate(Type type){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (type.getId()!=null) {
                int updateType = typeService.updateByPrimaryKey(type);
                ajaxResult.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("更新失败");

        }
        //将处理好的数据共享到页面进行显示
        return ajaxResult;

    }
    //删除项目分类
    @RequestMapping("/toDelete")
    @ResponseBody
    public Object toDelete(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (id!=null) {
                int insert = typeService.deleteByPrimaryKey(id);
                ajaxResult.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");

        }
        //将处理好的数据共享到页面进行显示
        return ajaxResult;

    }


    //批量删除
    @RequestMapping("toBatchDeleteType")
    @ResponseBody
    public Object toBatchDelete(DateVo data){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if (data.getTypeList().size()!=0) {
                int toBatchDeleteType=typeService.toBatchDeleteType(data);
                if (toBatchDeleteType!=0) {
                    ajaxResult.setSuccess(true);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("批量删除失败");
        }
        return ajaxResult;
    }


}
