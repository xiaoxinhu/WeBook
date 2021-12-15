package com.example.demo.service.impl;

import com.example.demo.mapper.SupplyMapper;
import com.example.demo.model.book.Book;
import com.example.demo.model.supply.Observer;
import com.example.demo.model.user.Message;
import com.example.demo.service.BookService;
import com.example.demo.service.MessageService;
import com.example.demo.service.SupplyService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("firstSupply")
public class SupplyServiceImp implements SupplyService {
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    SupplyMapper supplyMapper;
    @Autowired
    MessageService messageService;
    @Override
    public int addSupply(int id, Observer observer) {
        String account = observer.getAccount();
        return  supplyMapper.addSupply(id,account);
    }

    @Override
    public int removeSupply(int id, Observer observer) {
        String account = observer.getAccount();
        return supplyMapper.removeSupply(id,account);
    }

    @Override
    public List<Book> getSupplyByPage(int page, int pageSize) {
        return supplyMapper.getSupplyByPage(page,pageSize);
    }

    @Override
    public int notifyObservers(int id, String bookName) {

        List<String> supplyAccounts = supplyMapper.getSupplyAccount(id);
        for (String supplyAccount : supplyAccounts) {
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            String info = "您好，您订阅的"+bookName+"已重新上架,欢迎进行购买！";
            Message message = new Message();
            message.setDate(timestamp);
            message.setMessage(info);
            message.setIsRead(false);
            message.setAccount(supplyAccount);
            messageService.addMessage(message);
        }
         return 0;
       // return supplyMapper.notifyObservers(id);
    }

    @Override
    public void delSupply(int id) {
        supplyMapper.delSupply(id);
    }
}
