package com.example.demo.controller;

import com.example.demo.model.book.Book;
import com.example.demo.model.supply.Observer;
import com.example.demo.service.BookService;
import com.example.demo.service.SupplyService;
import com.example.demo.service.UserService;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(value = "/supply")
public class SupplyController {

    @Autowired
    @Qualifier("firstSupply")
    SupplyService supplyService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @GetMapping("/addSupply")
    public Map<String,Object> addCart(@RequestParam("id")int id,
                                      @RequestParam("account")String account){
        System.out.println("______________________----");
        Book book = bookService.getBook(id);
        System.out.println("id:"+id+"account:"+account);
        Observer observer = userService.getUser(account);
        System.out.println(observer);
        int result = book.attach(observer);
        if (result > 0){
            return ResultUtil.resultCode(200,"提交补单请求成功");
        }
        return ResultUtil.resultCode(500,"添提交补单请求失败");
//        if(cartService.existProduct(account,id)>0){//如果购物车中已经存在图书
//            return ResultUtil.resultCode(500,"购物车中已经存在该图书");
//        }
//        if(cartService.addProduct(account,id,num)>0){
//            return ResultUtil.resultCode(200,"添加到购物车成功");
//        }
//        return ResultUtil.resultCode(500,"添加到购物车失败");
    }
    /**
     * 按页得到需补单图书的集合
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getSupplyList")
    public Map<String, Object> getBook(@RequestParam(value = "page")int page,
                                       @RequestParam(value = "pageSize")int pageSize){
        System.out.println("=========================按页得到图书的集合========================");
        Map<String, Object> map = new HashMap<>();
        List<Book> bookList = supplyService.getSupplyByPage(page, pageSize);
        System.out.println(bookList);
        for(int i=0;i<bookList.size();i++){
            String img = bookService.getBookCover(bookList.get(i).getIsbn());
            System.out.println("=======设置了图书的封面图片========");
            bookList.get(i).setCoverImg(img);
        }
        map.put("bookList",bookList);
        int total = bookService.getBookCount();
        map.put("total",total);
        return ResultUtil.resultSuccess(map);
    }

    @GetMapping(value ="/modifyStock")
    public Map<String, Object > modifyStock(@RequestParam(value = "bookId")int bookId,
                                            @RequestParam(value = "stock")int stock) {
        Map<String, Object> map = new HashMap<>();
        int res = bookService.modifyStock(bookId,stock);
        if(res > 0){
           //通知读者
            Book book =bookService.getBook(bookId);
            book.notifyObservers();
            supplyService.delSupply(bookId);
            return ResultUtil.resultSuccess(map);
        }
        return ResultUtil.resultError(map);
    }

}
