package com.atguigu.atcrowdfunding.util;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 页面消息封装(json结果集)
 */
@NoArgsConstructor
public class AjaxResult {
    //成功标识
    private boolean success=true;
    //提示消息
    private String message;
    private PageResult page;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AjaxResult(boolean success, String message, PageResult page, Object data) {
        this.success = success;
        this.message = message;
        this.page = page;
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AjaxResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPage(PageResult page) {
        this.page = page;
    }

    public PageResult getPage() {
        return page;
    }
}
