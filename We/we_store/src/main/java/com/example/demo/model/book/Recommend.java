package com.example.demo.model.book;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Recommend {

    private int id;//编号
    private int bookId;//图书的编号
    private int grade;//推荐值
    private Timestamp addTime;//添加到推荐的时间
}
