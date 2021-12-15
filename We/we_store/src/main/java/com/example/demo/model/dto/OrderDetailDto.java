package com.example.demo.model.dto;


import com.example.demo.model.book.Book;
import lombok.Data;

/**
 * @author:
 * @date: 2020/7/22 10:35
 * @description: 订单明细类
 */
@Data
public class OrderDetailDto {
    private String orderId;//订单号
    private Book book; //图书
    private String num;//某本图书的下单数量
    private double price;//下单时候图书的单价


}
