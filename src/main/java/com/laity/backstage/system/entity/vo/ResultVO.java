package com.laity.backstage.system.entity.vo;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.List;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName ResultVO
 * @Description TODO
 * @createTime 2019/6/5/19:12
 */
public class ResultVO {
    //传递list列表
    private List list;
    //传递string数据
    private String str;
    //传对象或者map
    private Object obj;
    //传递url
    private String html;

    //错误码
    @TableField(exist = false)
    private String code = "1000";

    //错误或者提示信息
    @TableField(exist = false)
    private String msg = "everything is ok";

    //token
    @TableField(exist = false)
    private Serializable token;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Serializable getToken() {
        return token;
    }

    public void setToken(Serializable token) {
        this.token = token;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
