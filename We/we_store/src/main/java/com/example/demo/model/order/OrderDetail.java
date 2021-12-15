package com.example.demo.model.order;

import lombok.Data;

/**
 * @author:
 * @date: 2020/7/23 20:06
 * @description: 订单明细
 */
@Data
public class OrderDetail {
    private String orderId;//订单号
    private int bookId; //图书id
    private int num;//某本图书的下单数量
    private double price;//下单时候图书的单价


}
