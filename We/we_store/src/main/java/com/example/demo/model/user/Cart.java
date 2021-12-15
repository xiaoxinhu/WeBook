package com.example.demo.model.user;

import lombok.Data;

import java.sql.Timestamp;
/**
 * @author:
 * @date: 2020/7/22 21:46
 * @description: 购物车类
 */
@Data
public class Cart {
    private String account;
    private int id;
    private Integer num;
    private Timestamp addTime;//添加到购物车的时间

}
