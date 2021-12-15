package com.example.demo.mapper;


import com.example.demo.model.dto.OrderDetailDto;
import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderMapper {

    int addOrder(Order order);//添加总的订单

    int batchAddOrderDetail(List<OrderDetail> item);//批量添加订单明细
//
//    int delOrder(int id);//删除订单  这里涉及了多表删除  并且只能删除已经完成了的订单
//
//    int batchDelOrder(List<Integer> item);//批量删除 这里涉及多表联合删除
//
//    int modifyOrder(Order order);//修改订单 这里根据条件进行修改
//
//    int modifyLogistics(int id,int logisticsCompany,String logisticsNum);//修改物流信息
//
//    OrderDto findOrderDto(int id);//得到某个订单的所有信息明细
//
    List<OrderDetailDto> findOrderDetailDtoList(String orderId);//得到指定订单所有图书的明细信息

    //按页得到所有订单的明细 userId为空则为管理员，userId不为空则为普通用户
    List<OrderDto> orderDtoList(@Param("userId") String userId,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize, @Param("orderStatus") String orderStatus, @Param("beUserDelete") boolean beUserDelete);

    //得到订单的总数 id值的意义和得到订单明细列表方法id一样
    int count(@Param("userId")String userId,@Param("orderStatus")String orderStatus,@Param("beUserDelete") boolean beUserDelete);

//    //得到订单的统计数据
//    List<OrderStatistic> getOrderStatistic(Timestamp beginDate, Timestamp endDate);
}
