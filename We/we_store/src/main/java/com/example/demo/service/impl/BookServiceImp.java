package com.example.demo.service.impl;


import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.SortMapper;
import com.example.demo.model.book.Book;
import com.example.demo.model.book.BookImg;
import com.example.demo.model.book.BookSort;
import com.example.demo.model.book.Recommend;
import com.example.demo.model.dto.OrderBookDto;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service("firstVersion")
public class BookServiceImp implements BookService {

    private static final String book_prefix="bookStore_book_";
    private static final String bookList_prefix="bookStore_bookList";
    private static final String book_stock="book_stock_";

    @Autowired
    BookMapper bookMapper;

    @Autowired
    SortMapper sortMapper;

    @Override
    public int addBook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    public int getBookId(String getisbn) {
        return bookMapper.getBookId(getisbn);
    }

    //先得到对应分类名所有分类的id号的集合，然后按页得到一级分类的图书集合
    @Override
    public List<Book> getBooksByFirst(String sortName, int page, int pageSize) {
        int start = (page-1)*pageSize;
        List<Integer> item = sortMapper.getAllFirSortId(sortName);
        return bookMapper.getBooksByFirst(item,start,pageSize);
    }
    @Override
    public int getPublishBookNum(String publishName) {
        return bookMapper.getPublishBookNum(publishName);
    }

    @Override
    public List<Book> getBooksByPage(int page, int pageSize) {
        int start = (page-1)*pageSize;
        return bookMapper.getBooksByPage(start,pageSize);
    }


    @Override
    public String getBookCover(String isbn) {
        return bookMapper.getBookCover(isbn);
    }

    @Override
    public int getBookCount() {
        return bookMapper.getBookCount();
    }

    @Override
    public int addBookImg(BookImg bookImg) {
        int result = bookMapper.addBookImg(bookImg);
        return result;
    }

    @Override
    public List<String> getBookImgSrcList(String isbn) {
        return bookMapper.getBookImgSrcList(isbn);
    }

    @Override
    public int modifyBookPut(int bookId, boolean put) {
        return bookMapper.modifyBookPut(bookId,put);
    }

    @Override
    public int hasExistInRec(int bookId) {
        return bookMapper.hasExistInRec(bookId);
    }

    @Override
    public void deleteFromRecommend(int bookId) {
        bookMapper.deleteFromRecommend(bookId);
    }

    @Override
    public void addToRecommend(Recommend r) {
        bookMapper.addToRecommend(r);
    }

    @Override
    public void modifyBookRec(int bookId, boolean recommend) {
        bookMapper.modifyBookRec(bookId,recommend);
    }

    @Override
    public void modifyBookNewPro(int bookId, boolean newProduct) {
        bookMapper.modifyBookNewPro(bookId,newProduct);
    }

    @Override
    public void addToNewProduct(Recommend r) {
        bookMapper.addToRecommend(r);
    }

    @Override
    public void deleteFromNewProduct(int bookId) {
        bookMapper.deleteFromNewProduct(bookId);
    }

    @Override
    public int hasExistInNew(int bookId) {
        return bookMapper.hasExistInNew(bookId);
    }

    @Override
    public int deleteBook(int bookId) {
        return bookMapper.deleteBook(bookId);
    }

    @Override
    public Book getBook(int bookId) {
        return bookMapper.getBook(bookId);
    }

    @Override
    public int deleteBookImgOfOne(String getisbn) {
        return bookMapper.deleteBookImgOfOne(getisbn);
    }

    @Override
    public List<Book> getRecommendsByPage(int page, int pageSize) {
        return bookMapper.getRecommendsByPage(page,pageSize);
    }

    @Override
    public List<Book> getNewProductsByPage(int page, int pageSize) {
        return bookMapper.getNewProductsByPage(page,pageSize);
    }

    @Override
    public List<Book> getNewPutBookList(int page, int pageSize) {
        return bookMapper.getNewPutBookList(page,pageSize);
    }

    @Override
    public List<Book> getBookBySecond(int bookSortId, int page, int pageSize) {
        return bookMapper.getBookBySecond(bookSortId,page,pageSize);
    }


    @Override
    public int getFirstBookCount(String sortName) {
        List<Integer> item = sortMapper.getAllFirSortId(sortName);
        return bookMapper.getFirstBookCount(item);
    }

    @Override
    public int getSecondBookCount(int id) {
        return bookMapper.getSecondBookCount(id);
    }

    @Override
    public int addBookToSort(int bookSortId, int bookId) {
        return bookMapper.addBookToSort(bookSortId,bookId);
    }


    @Override
    public BookSort getBookSort(int bookId) {
        return bookMapper.getBookSort(bookId);
    }

    @Override
    public List<OrderBookDto> getBatchBookList(int[] ids) {
        return bookMapper.getBatchBookList(ids);
    }

    @Override
    public List<OrderBookDto> getOneBookList(int[] ids) {
        return bookMapper.getOneBookList(ids);
    }

    @Override
    public int modifyStock(int bookId,int stock) {
        return bookMapper.modifyBookStock(bookId,stock);
    }


    @Override
    public int batchDelBook(int[] idS) {
        for(int i=0;i<idS.length;i++){
            if(bookMapper.deleteBook(idS[i])<1){
                return 0;
            };
        }
        return 1;
    }

    @Override
    public int batchPutBook(int[] idS, boolean put) {
        for(int i=0;i<idS.length;i++){
            if(bookMapper.modifyBookPut(idS[i],put)<1){
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int batchRecBook(int[] idS, boolean recommend) {
        Recommend r = new Recommend();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        r.setAddTime(timestamp);
        if(recommend==true){
            for(int i=0;i<idS.length;i++){
                int isExistInRec = bookMapper.hasExistInRec(idS[i]);
                if(isExistInRec==0){
                    r.setBookId(idS[i]);
                    bookMapper.addToRecommend(r);
                }
                if(bookMapper.modifyBookRec(idS[i],true)<1){
                    return 0;
                }
            }
        }else {
            for(int i=0;i<idS.length;i++){
                bookMapper.modifyBookRec(idS[i],false);
                bookMapper.deleteFromRecommend(idS[i]);
            }
        }
        return 1;
    }

    @Override
    public int batchNewProBook(int[] idS, boolean newProduct) {
        Recommend r = new Recommend();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        r.setAddTime(timestamp);
        if(newProduct){
            for(int i=0;i<idS.length;i++){
                int isExistInNew = bookMapper.hasExistInNew(idS[i]);
                if(isExistInNew==0){
                    r.setBookId(idS[i]);
                    bookMapper.addToNewProduct(r);
                }
                if(bookMapper.modifyBookNewPro(idS[i],true)<1){
                    return 0;
                }
            }
        }else {
            for(int i=0;i<idS.length;i++){
                bookMapper.modifyBookNewPro(idS[i],false);
                bookMapper.deleteFromNewProduct(idS[i]);
            }
        }
        return 1;
    }

    @Override
    public List<Book> getBookByName(String name,int page,int pageSize) {
        return bookMapper.getBookByName(name,page,pageSize);
    }

}
