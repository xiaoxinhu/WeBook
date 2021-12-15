package com.example.demo.model.dto;


import com.example.demo.model.book.Book;

import java.util.List;

/**
 *
 *
 * @description: 某一个子分类的和这个子分类所包含的图书
 */
public class SortBookRes {
     private int sortId;
     private String sortName;
     private List<Book> bookList;

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "SortAndBook{" +
                "sortId=" + sortId +
                ", sortName='" + sortName + '\'' +
                ", bookList=" + bookList +
                '}';
    }
}
