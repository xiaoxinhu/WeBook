package com.example.demo.service;

import com.example.demo.model.book.Book;
import com.example.demo.model.supply.Observer;

import java.util.List;

public interface SupplyService {
    int addSupply(int id, Observer observer);

    int removeSupply(int id, Observer observer);

    List<Book> getSupplyByPage(int page, int pageSize);

    int notifyObservers(int id, String bookName);

    void delSupply(int id);
}
