package com.example.demo.model.book;

import com.example.demo.util.GetBeanUtil;
import com.example.demo.model.supply.Observer;
import com.example.demo.model.supply.Subject;
import com.example.demo.service.SupplyService;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class Book  implements Subject {
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
    private List<String> ImgSrc;//书的图片地址
    private boolean put;//是否上架
    private String coverImg;//书的封面图
    private int rank;//权重值
    private boolean newProduct;
    private boolean recommend;
    private int bookSort[];
    private List<Observer> observers = new ArrayList<>();
    private String msg;

    SupplyService supplyService = GetBeanUtil.getBean(SupplyService.class);
    @Override
    public int attach(Observer observer) {
        //注册该用户为该图书的观察者
        return supplyService.addSupply(this.id,observer);
    }

    @Override
    public int detach(Observer observer) {
        return supplyService.removeSupply(this.id,observer);
    }

    @Override
    public int notifyObservers() {
        //通知 同时删除表中关系
        return supplyService.notifyObservers(this.id,this.bookName);
    }
}
