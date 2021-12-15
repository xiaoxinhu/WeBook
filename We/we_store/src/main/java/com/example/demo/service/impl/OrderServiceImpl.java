package com.example.demo.service.impl;

import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.ExpenseMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.dto.OrderBookDto;
import com.example.demo.model.dto.OrderDetailDto;
import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.dto.OrderInitDto;
import com.example.demo.model.order.Expense;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderDetail;
import com.example.demo.model.order.OrderStatusEnum;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: 黄龙
 * @date: 2020/7/22 8:59
 * @description:
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ExpenseMapper expenseMapper;

    @Autowired
    BookMapper bookMapper;



    private static final String stock_prefix="stock_";//这个用来设置锁

    private static final String book_stock="book_stock_";//这个用来存储库存的缓存

    private static final String book_prefix="bookStore_book_";//这个用来存储单个图书的数据

    private static final String bookList_prefix="bookStore_bookList";//图书集合中数据

    public String initOrderId() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }


    @Transactional
    @Override
    public boolean addOrder(OrderInitDto orderInitDto) {
        Order order = new Order();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String orderId = initOrderId();
        order.setOrderId(orderId);
        System.out.println("============orderInitDto.getAccount():==========="+orderInitDto.getAccount()+"============");
        order.setAccount(orderInitDto.getAccount());
        order.setAddressId(orderInitDto.getAddress().getId());//收货地址编号
        order.setOrderTime(timestamp);
        order.setOrderStatus(OrderStatusEnum.SUBMIT.getName());
        order.setBeUserDelete(false);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(OrderBookDto orderBookDto :orderInitDto.getBookList()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBookId(orderBookDto.getId());
            orderDetail.setNum(orderBookDto.getNum());
            orderDetail.setPrice(orderBookDto.getPrice());
            orderDetail.setOrderId(orderId);
            orderDetailList.add(orderDetail);
            System.out.println("=====orderDetail.toString()====="+orderDetail.toString());
            String clientId = UUID.randomUUID().toString();
            try{
//                Boolean result = redisTemplate.opsForValue().setIfAbsent(stock_prefix+orderBookDto.getId(), clientId, 10, TimeUnit.SECONDS);
//                if(!result){
//                    return false;//获取分布式锁出错了！
//                }
//                if(redisTemplate.hasKey(book_stock+orderBookDto.getId())){//如果缓存中有库存数据
//                    int stock = Integer.parseInt((String) redisTemplate.opsForValue().get(book_stock+orderBookDto.getId()));
//                    if(stock>orderBookDto.getNum()){
//                        int realStock = stock-orderBookDto.getNum();
//                        redisTemplate.opsForValue().set(book_stock+orderBookDto.getId(),realStock);//更新库存缓存
//                        ValueOperations<String, Book> operations = redisTemplate.opsForValue();
//                        if(redisTemplate.hasKey(book_prefix+orderBookDto.getId())){
//                            System.out.println("=========从缓存中读取数据==========");
//                            Book book = operations.get(book_prefix + orderBookDto.getId());
//                            book.setStock(realStock);
//                            redisTemplate.opsForValue().set(book_prefix+book.getId(),book);//更新图书缓存中的数据
//                        }
//                        Book book = bookMapper.getBook(orderBookDto.getId());
//                        redisTemplate.opsForValue().set(book_prefix+book.getId(),book);//更新图书缓存中的数据
////                        redisTemplate.opsForZSet().remove(book);//删除集合中原来的图书数据
//                        book.setStock(realStock);
////                        redisTemplate.opsForZSet().add(bookList_prefix,book,book.getRank());//添加新的图书数据到缓存集合中去
//                        try{
//                            System.out.println("================开始减库存====================");
//                            int update = bookMapper.modifyBookStock(orderBookDto.getId(), orderBookDto.getNum());//减去库存
//                            System.out.println("==============减去库存=====================");
//                        }catch (Exception e){
//                            redisTemplate.opsForValue().set(book_stock+orderBookDto.getId(),stock);//恢复缓存中的库存数量，避免少买
////                            redisTemplate.opsForZSet().remove(book);//删除集合中原来的图书数据
//                            book.setStock(stock);
//                            redisTemplate.opsForValue().set(book_prefix+book.getId(),book);//更新图书缓存中的数据
////                            redisTemplate.opsForZSet().add(bookList_prefix,book,book.getRank());//添加新的图书数据到缓存集合中去
//                        }
//                        System.out.println("=============减去库存没有问题======================");
//                    }else {
//                        throw new Exception("=====库存不足========");
//                    }
//                }else{
                    int update = bookMapper.modifyBookStock(orderBookDto.getId(), orderBookDto.getNum());//减去库存
                    if(update<1){
                        System.out.println("=====库存不足========");
                        return false;
                    }
//                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
//                if(clientId.equals(redisTemplate.opsForValue().get(stock_prefix+orderBookDto.getId()))){
//                    redisTemplate.delete(stock_prefix+orderBookDto.getId());
//                }
            }
        }
        orderMapper.addOrder(order);//添加总的订单

        orderMapper.batchAddOrderDetail(orderDetailList);//批量添加订单明细
        System.out.println("==============添加订单明细成功==============");

        Expense expense = orderInitDto.getExpense();
        expense.setOrderId(orderId);
        expenseMapper.addExpense(expense);//订单订单费用到费用表中
        System.out.println("===========添加订单费用成功==============");
        return true;
    }

//    @Override
//    public int delOrder(int id) {
//        return orderMapper.delOrder(id);
//    }
//
//    @Override
//    public int userDelOrder(int id) {
//        Order order = new Order();
//        order.setId(id);
//        order.setBeUserDelete(true);
//        return orderMapper.modifyOrder(order);
//    }
//
//    @Override
//    public int batchDelOrder(List<Integer> item) {
//        return orderMapper.batchDelOrder(item);
//    }
//
//    @Override
//    public int modifyOrderStatus(int id, String orderStatus) {
//        Order order = new Order();
//        order.setId(id);
//        order.setOrderStatus(orderStatus);
//        return orderMapper.modifyOrder(order);
//    }
//
//    @Transactional
//    @Override
//    public int deliverBook(int id, int logisticsCompany, String logisticsNum) {
//        int result = orderMapper.modifyLogistics(id, logisticsCompany, logisticsNum);
//        Order order = new Order();
//        order.setId(id);
//        order.setOrderStatus("已发货");
//        orderMapper.modifyOrder(order);
//        return 1;
//    }
//
//    @Override
//    public OrderDto findOrderDto(int id) {
//        OrderDto orderDto = new OrderDto();
//        orderDto = orderMapper.findOrderDto(id);
//        List<OrderDetailDto> orderDetailDtoList  = orderMapper.findOrderDetailDtoList(orderDto.getOrderId());
//        for(int i=0;i<orderDetailDtoList.size();i++){
//            System.out.println("======="+orderDetailDtoList.get(i).toString()+"=====");
//        }
//        orderDto.setOrderDetailDtoList(orderDetailDtoList);
//        return orderDto;
//    }
//
    @Override
    public List<OrderDetailDto> findOrderDetailDtoList(String orderId) {
        return orderMapper.findOrderDetailDtoList(orderId);
    }
//
    @Override
    public List<OrderDto> orderDtoList(String userId, int pageNum, int pageSize, String orderStatus, boolean beUserDelete) {
        pageNum = (pageNum-1)*pageSize;
        return orderMapper.orderDtoList(userId, pageNum, pageSize,orderStatus,beUserDelete);
    }
//
    @Override
    public int count(String userId,String orderStatus,boolean beUserDelete) {
        return orderMapper.count(userId,orderStatus,beUserDelete);
    }

//    @Override
//    public List<OrderStatistic> getOrderStatistic(String beginDate, String endDate) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = format.parse(beginDate);
//        Date date1 = format.parse(endDate);
//
//        return orderMapper.getOrderStatistic(new Timestamp(date.getTime()), new Timestamp(date1.getTime()));
//    }
}
