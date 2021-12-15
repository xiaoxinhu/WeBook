package com.example.demo.model.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author:
 * @date: 2020/7/22 21:52
 * @description: 用来描述订单明细中的图书信息
 */
@Data
public class OrderBookDto {
    private int id;
    private String bookName;
    private String author;
    private String isbn;
    private String publish;//出版社
    private Timestamp birthday;//出版时间
    private double marketPrice;//书的原价
    private double price;//书的售价
    private Integer stock;//库存
    private String description;//书的概述，在详情页展示
    private String coverImg;//书的封面图
    private int num;//购买的数量

}
