package com.example.demo.model.dto;


import com.example.demo.model.order.Expense;
import com.example.demo.model.user.Address;
import lombok.Data;

import java.util.List;

;

/**
 * @author:
 * @date: 2020/7/22 21:09
 * @description: 初始化订单的交互类
 */
@Data
public class OrderInitDto {
    private String account;//用户账号
    private List<OrderBookDto> bookList;
    private List<Address> addressList;//返回给前端显示的某个用户的地址
    private Expense expense;
    private Address address;

}
