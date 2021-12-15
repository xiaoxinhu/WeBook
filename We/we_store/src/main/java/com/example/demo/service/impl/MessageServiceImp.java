package com.example.demo.service.impl;

import com.example.demo.mapper.MessageMapper;
import com.example.demo.model.user.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Override
    public List<Message> getMessage(String account) {
        return messageMapper.getMessage(account);
    }

    @Override
    public int deleteMessage(int id) {
        return messageMapper.deleteMessage(id);
    }

    @Override
    public int addMessage(Message message) {
        return messageMapper.addMessage(message);
    }
}
