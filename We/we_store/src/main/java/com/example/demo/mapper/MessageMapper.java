package com.example.demo.mapper;

import com.example.demo.model.user.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    List<Message> getMessage(String account);
    int deleteMessage(int id);
    int addMessage(Message message);
}
