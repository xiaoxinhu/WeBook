package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.book.Book;
import com.example.demo.model.book.BookImg;
import com.example.demo.model.book.BookSort;
import com.example.demo.model.book.Recommend;
import com.example.demo.model.dto.OrderBookDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper extends BaseMapper<Book> {
    //对于book表的操作
    int addBook(Book book);
    int getBookId(String getisbn);
    int getPublishBookNum(String publishName);//得到某一出版社的图书的数量
    List<Book> getBooksByPage(@Param("page") int page, @Param("pageSize") int pageSize);//按页得到图书集合
    String getBookCover(String isbn);//得到书的封面图
    int getBookCount();
    int addBookImg(BookImg bookImg);
    List<String> getBookImgSrcList(String isbn);//按isbn号得到某本图书的所有图书集合
    int modifyBookPut(@Param(value = "id") int id,@Param(value = "put") boolean put);//修改图书是否为上下架
    int hasExistInRec(int bookId);
    void deleteFromRecommend(int bookId);
    void addToRecommend(Recommend r);
    int modifyBookRec(@Param(value = "bookId") int bookId,@Param(value = "recommend") boolean recommend);
    int modifyBookNewPro(@Param(value = "bookId") int bookId, @Param(value = "newProduct") boolean newProduct);
    void addToNewProduct(Recommend r);
    void deleteFromNewProduct(int bookId);
    int hasExistInNew(int bookId);
    int deleteBook(int bookId);

    Book getBook(int bookId);

    int deleteBookImgOfOne(String getisbn);

    List<Book> getRecommendsByPage(@Param("page") int page, @Param("pageSize") int pageSize);

    List<Book> getNewProductsByPage(@Param("page") int page, @Param("pageSize") int pageSize);

    List<Book> getNewPutBookList(@Param("page") int page, @Param("pageSize") int pageSize);

    List<Book> getBookBySecond(@Param("bookSortId") int bookSortId,@Param("page") int page,@Param("pageSize") int pageSize);//得到级别二的所有分类图书
    int getFirstBookCount(List<Integer> list);//得到一级分类图书的数量

    int getSecondBookCount(int id);

    int addBookToSort(@Param("bookSortId") int bookSortId ,@Param("bookId") int bookId);

    List<Book> getBooksByFirst(@Param("list") List<Integer> list,@Param("page") int page,@Param("pageSize") int pageSize);//得到级别一的所有分类书籍

    BookSort getBookSort(int bookId);//得到某本书的分类名

    List<OrderBookDto> getBatchBookList(int[] ids);//根据ids数组，得到对应的图书集合

    List<OrderBookDto> getOneBookList(int[] ids);//根据ids数组，得到对应的图书集合

    int modifyBookStock(@Param("id") int id,@Param("stockNum") int stockNum);//减库存stockNum

    List<Book> getBookByName(@Param("name") String name, @Param("page") int page, @Param("pageSize") int pageSize);
    //批量处理图书
//    int batchDelBook(int[] idS);
//    int batchPutBook(int[] idS,boolean put);
//    int batchRecBook(int[] idS,boolean recommend);
//    int batchNewProBook(int[] idS,boolean newProduct);
}

