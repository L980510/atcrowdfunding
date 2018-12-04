package com.atguigu.atcrowdfunding.vo;

import com.atguigu.atcrowdfunding.bean.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class DateVo {

    private List<User> userList = new ArrayList<User>();
    private List<User> datas = new ArrayList<User>();
    private List<Role> roleList = new ArrayList();
    private List<Param> paramList = new ArrayList();
    private List<Cert> certList = new ArrayList();
    private List<Cert> typeList = new ArrayList();
    private List<Advertisement> advertisementList = new ArrayList();


    private List<Integer> ids=new ArrayList<Integer>() ;


}
