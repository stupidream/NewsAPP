package com.txs.entity;


import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/7/9.
 */

public class Users extends BmobObject {
    private String phone;
    private String password;
    private String nickname;
    private String sex;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}
