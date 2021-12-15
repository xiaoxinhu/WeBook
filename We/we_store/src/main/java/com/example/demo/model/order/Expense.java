package com.example.demo.model.order;

import lombok.Data;

/**
 * @author:
 * @date: 2020/7/22 9:37
 * @description: 订单费用
 */
@Data
public class  Expense {
    private String orderId;//订单编号
    private double productTotalMoney; //商品总价
    private double freight;//运费 默认为0元
    private int coupon;//优惠券 默认为0元
    private double activityDiscount;//活动优惠 默认为0元
    private double allPrice;//订单总金额
    private double finallyPrice;//最终实付总额


}
