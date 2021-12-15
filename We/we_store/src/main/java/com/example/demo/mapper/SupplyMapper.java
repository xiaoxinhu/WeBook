package com.example.demo.mapper;

import com.example.demo.model.book.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyMapper {

    int addSupply(@Param("id") int id, @Param("account") String account);

    int removeSupply(@Param("id") int id, @Param("account") String account);

    List<Book> getSupplyByPage(@Param("page") int page, @Param("pageSize") int pageSize);

    List<String> getSupplyAccount(int id);

    void delSupply(int id);

    //   int notifyObservers(int id);

}
