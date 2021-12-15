package com.example.demo.service;

import com.example.demo.model.user.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    List<Message> getMessage(String account);
    int deleteMessage(int id);
    int addMessage(Message message);

}
