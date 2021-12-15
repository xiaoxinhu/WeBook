package com.example.demo.model.user;

import com.example.demo.model.supply.Observer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class User implements Observer {
    private int id;//用户编号
    private String account;//用邮箱注册
    private String password;
    private String name;
    private String gender;
    private String imgUrl;
    private String info;//个人简介
    private boolean manage;//是否为管理员
    private boolean enable;//是否禁用
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp registerTime;//注册时间

    @Override
    public void update(int bookId) {
        //可以获取到自己这个用户的id对吧因此可以进行 哪个
    }
    @Override
    public String getAccount() {
        return this.account;
    }
}
