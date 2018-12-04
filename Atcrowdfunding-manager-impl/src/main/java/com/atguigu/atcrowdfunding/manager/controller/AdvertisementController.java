package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.AdvertisementService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.PageResult;
import com.atguigu.atcrowdfunding.vo.DateVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * 广告管理控制层
 */
@Controller
@RequestMapping("advertisement")
public class AdvertisementController extends BaseController{

    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 跳转到广告管理首页
     * @return
     */
    @RequestMapping("tolist")
    public String toList(){
        return "advertisement/index";
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
            PageResult pageResult=advertisementService.queryAdvertisementPageData(map);
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

    //添加广告管理
    @RequestMapping("add")
    public String toAdd(){
        return "advertisement/toAdd";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(HttpServletRequest request, Advertisement advertisement, HttpSession session){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //拿到文件上传对象
            MultipartHttpServletRequest multipartHttpServletRequest=
                    (MultipartHttpServletRequest)request;
            //通文件上传对象拿到你要文件上传的文件名称
            MultipartFile file =
                    multipartHttpServletRequest.getFile("advpic");
            //通过拿到要文件上传的文件调用方法来获取文件名称
            String filename = file.getOriginalFilename();
            //进行截取.jsp
            String substringSuffix = filename.substring(filename.lastIndexOf("."));
           //进行拼接
            String iconPath = UUID.randomUUID().toString() + substringSuffix;//xx-sdd-f45.jsp
            //获取内置对象
            ServletContext servletContext = session.getServletContext();
            //获取真实路径（绝对路径）
            String realPath = servletContext.getRealPath("/pics");
            //
            String path = realPath + "\\adv\\" + iconPath;
            //进行保存到系统路径上
            file.transferTo(new File(path));
            //因为页面只传了广告名称，广告地址，广告图片
            //而数据库中广告还需要保存广告状态，是谁保存的等
            User user = (User) session.getAttribute(Const.LOGIN_USER);
            advertisement.setStatus("1");
            advertisement.setUserid(user.getId());
            advertisement.setIconpath(iconPath);
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            int insert = advertisementService.insert(advertisement);
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
        Advertisement advertisement = advertisementService.selectByPrimaryKey(id);
        map.put("advertisement",advertisement);
        return "advertisement/toUpdate";
    }

    @RequestMapping("/toUpdate")
    @ResponseBody
    public Object doUpdate(Advertisement advertisement){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (advertisement.getId()!=null) {
                int updateAdvertisement = advertisementService.updateByPrimaryKey(advertisement);
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
    //删除广告管理
    @RequestMapping("/toDelete")
    @ResponseBody
    public Object toDelete(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            //获取请求参数并且封装到map集合中
            //调用业务方法处理请求
            if (id!=null) {
                int insert = advertisementService.deleteByPrimaryKey(id);
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
    @RequestMapping("toBatchDeleteAdvertisement")
    @ResponseBody
    public Object toBatchDelete(DateVo data){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if (data.getAdvertisementList().size()!=0) {
                int toBatchDeleteAdvertisement=advertisementService.toBatchDeleteAdvertisement(data);
                if (toBatchDeleteAdvertisement!=0) {
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
