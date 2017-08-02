package com.example.ticket.entity.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/25.
 */

public class User implements Serializable {

    /**
     * uid :
     * phone : 请核对用户名和密码
     * screen_name :
     * sex :
     * name :
     * userpwd :
     * complete : false
     * idNum :
     */

    private String uid;
    private String phone;
    private String screen_name;
    private String sex;
    private String name;
    private String userpwd;
    private boolean complete;
    private String idNum;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", userpwd='" + userpwd + '\'' +
                ", complete=" + complete +
                ", idNum='" + idNum + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
