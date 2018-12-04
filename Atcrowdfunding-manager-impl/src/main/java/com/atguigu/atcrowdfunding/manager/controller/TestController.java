package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试整个流程是否通
 */
@Controller
public class TestController {
    /*
    * ------------------------------------------@Autowired注解是这么依赖注入：--------------------------------------------
1.首先根据byType进行类型查找
2.如果查找到1个，就直接注入
2.1如果查找到多个（一个接口有多个实现类）那么这么注入？
根据byNane进行注入
        将多个对象中其中名称和变量名称一致的那个bean注入进来
2.2如果找到多个对象没有与名称与变量名称一致的那么？
可以指定@Qualifier（value=“xxxx”），将其中一个注入进来，
如果@Qualifier所指定的名称，没有与任何一个bean名称一致，会报错，
如果无法注入，不希望保存：@Autowired（require=false）

* */
    @Autowired
    private TestService testService;

    /**
     * AOP的原理：动态代理
     * 如果目标对象 有接口，那么默认采用的是JDK动态代理（基于接口（代理类和目标类实现共同接口））；
     如果目标对象 没有接口，那么采用的是Cglib动态代理（基于继承（代理对象是目标对象的子类））
     推荐使用JDK动态代理，也就是我们推荐采用面向接口编程，面向抽象编程；
     * @return
     */
    @RequestMapping("test")
    public String test(){
        System.out.println("测试成功");
        //调用业务层方法
        testService.insert();
        System.out.println("=====================");
        return "test/success";
    }
}
