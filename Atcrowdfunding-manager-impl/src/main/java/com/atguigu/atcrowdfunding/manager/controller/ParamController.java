package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Param;
import com.atguigu.atcrowdfunding.bean.Type;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.ParamService;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.TypeService;
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
 * 参数控制层
 */
@Controller
@RequestMapping("param")
public class ParamController extends BaseController{

    @Autowired
    private ParamService paramService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 跳转到参数首页
     * @return
     */
    @RequestMapping("tolist")
    public String toList(){
        return "param/index";
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
            PageResult pageResult=paramService.queryParamPageData(map);
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

    //添加参数
    @RequestMapping("add")
    public String toAdd(){
        return "param/toAdd";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Param param){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            int insert = paramService.insert(param);
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
        Param param = paramService.selectByPrimaryKey(id);
        map.put("params",param);
        return "param/toUpdate";
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Object doUpdate(Param param){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (param.getId()!=null) {
                int updateParam = paramService.updateByPrimaryKey(param);
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
    //删除参数
    @RequestMapping("/toDelete")
    @ResponseBody
    public Object toDelete(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (id!=null) {
                int insert = paramService.deleteByPrimaryKey(id);
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
    @RequestMapping("toBatchDeleteParam")
    @ResponseBody
    public Object toBatchDelete(DateVo data){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if (data.getParamList().size()!=0) {
                int toBatchDeleteParam=paramService.toBatchDeleteParam(data);
                if (toBatchDeleteParam!=0) {
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
