package com.example.demo.service;


import com.example.demo.model.book.Book;
import com.example.demo.model.book.BookImg;
import com.example.demo.model.book.BookSort;
import com.example.demo.model.book.Recommend;
import com.example.demo.model.dto.OrderBookDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    int addBook(Book book);

    int getBookId(String getisbn);

    int getPublishBookNum(String publishName);//得到某一出版社的图书的数量

    List<Book> getBooksByFirst(String sortName, int page, int pageSize);

    List<Book> getBooksByPage(int page,int pageSize);

    String getBookCover(String isbn);//得到书的封面图

    int getBookCount();

    int addBookImg(BookImg bookImg);

    List<String> getBookImgSrcList(String isbn);

    int modifyBookPut(int bookId, boolean put);

    int hasExistInRec(int bookId);

    void deleteFromRecommend(int bookId);

    void addToRecommend(Recommend r);

    void modifyBookRec(int bookId, boolean recommend);

    void modifyBookNewPro(int bookId, boolean newProduct);

    void addToNewProduct(Recommend r);

    void deleteFromNewProduct(int bookId);

    int hasExistInNew(int bookId);

    int deleteBook(int bookId);

    Book getBook(int bookId);

    int deleteBookImgOfOne(String getisbn);

    List<Book> getRecommendsByPage(int page,int pageSize);

    List<Book> getNewProductsByPage(int page,int pageSize);

    List<Book> getNewPutBookList(int page,int pageSize);

    List<Book> getBookBySecond(int bookSortId,int page,int pageSize);//得到级别二的所有分类图书

    int getFirstBookCount(String sortName);

    int getSecondBookCount(int id);

    int addBookToSort(int bookSortId ,int bookId);

    BookSort getBookSort(int bookId);//得到某本书的分类名

    List<OrderBookDto> getBatchBookList(int[] ids);//根据ids数组，得到对应的图书集合

    List<OrderBookDto> getOneBookList(int[] ids);//根据ids数组，得到对应的图书集合

    int modifyStock(int bookId,int stock);

    //批量处理图书
    int batchDelBook(int[] idS);
    int batchPutBook(int[] idS,boolean put);
    int batchRecBook(int[] idS,boolean recommend);
    int batchNewProBook(int[] idS,boolean newProduct);

    List<Book> getBookByName(String name,int page,int pagesize);
}
