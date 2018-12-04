package com.atguigu.atcrowdfunding.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class PageResult {
    //两传（页面用户传过来用户查询的当前页和页面大小）
    private Integer currentPage;
    private Integer pageSize;
    //两计算
    private Integer totalCount;//总记录数
    private List datas;//每一页显示的数据
    private  Integer totalno;//总页数

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public List getDatas() {
        return datas;
    }

    public PageResult(Integer currentPage, Integer pageSize) {
        //当前页和页面大小是需要默认值的否则包null，
        if (currentPage<=0) {
            this.currentPage = 1;
        }else{
            this.currentPage = currentPage;
        }
        if (pageSize<=0) {
            this.pageSize = 10;
        }else{
            this.pageSize = pageSize;
        }

    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        this.totalno=totalCount%pageSize ==0 ? (totalCount/pageSize):(totalCount/pageSize+1);
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }

    public Integer getTotalno() {
        return totalno;
    }

    public void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }
    //计算起始页
    public Integer getStartIndex(){
        return (currentPage-1)*pageSize;
    }
}
