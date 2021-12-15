package com.example.demo.mapper;


import com.example.demo.model.dto.CartBookDto;
import com.example.demo.model.user.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    int addProduct(Cart cart); //添加图书
    int existProduct(@Param("account") String account,@Param("id") int id);//判断购物车中是否已经存在某本图书
//    int deleteProduct(String account,int id);//删除图书
    int delBatchProduct(@Param("account") String account,@Param("ids") int[] ids);//批量删除图书
//    int modifyProductNum(Cart cart);//修改图书
    List<CartBookDto> getCartsByPage(@Param("account") String account, @Param("page") int page,@Param("pageSize") int pageSize);//按页得到某账号的购物车信息
//    int count(String account);//得到某人的购物车商品总数
    int getBookCount(@Param("account") String account,@Param("id") int id);//得到某本书在购物车中的数量
}
