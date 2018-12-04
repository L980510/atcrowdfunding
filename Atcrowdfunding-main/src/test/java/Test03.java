import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class Test03 {


    public static  void main(String[]args) throws Exception {
        //=============方式1会报空指针异常==============
        //Exception in thread "main" java.lang.NullPointerException
       // String str=null;
       // str.toUpperCase();//成员变量：依赖对象才能调用
        //==============方式2：不会报错==================
       // Thread thread=null;
        //thread.sleep(3000);//静态方法，正常情况下是通过类名称调用，而不推荐用对象调用
        //===============方式3：会报NumberFormatException: null=====================
        //String i=null;
        //int j = Integer.parseInt(i);
        //System.out.println(j);//不能将一个null对象解析成int对象
        //============方式4:会报NullPointerException=================
       // Integer i=null;
       // int j=i;//自动拆箱
       // System.out.println(j);
        //========方式5：NullPointerException=================================
      /*  List<String>list=null;
       *//* for (String s : list) {
            System.out.println(s);
        }*//*
       for (int i=0;i<list.size();i++){
           System.out.println("i="+i);
       }*/
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring*.xml");
        UserService contextBean = applicationContext.getBean(UserService.class);
        for (int i=1;i<66;i++) {
            User user = new User();
            user.setLoginAcct("test"+i);
            user.setUsername("test"+i);
            user.setCreateTime("2017-08-23 14:17:00");
            user.setUserPswd(MD5Util.digest("123"));
            user.setEmail("test"+i);
            contextBean.insert(user);
        }
    }


}
