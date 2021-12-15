package com.example.demo.controller;

import com.example.demo.model.user.Message;
import com.example.demo.service.MessageService;
import com.example.demo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/message")
public class MessageConrtoller {
    @Autowired
    MessageService messageService;
    @GetMapping(value = "/getMessageList")
    public Map<String, Object> getMessage(@RequestParam(value = "account") String account){
        Map<String, Object> map = new HashMap<>();
        List<Message> messageList = messageService.getMessage(account);
        map.put("messageList",messageList);
        map.put("total",messageList.size());
        return ResultUtil.resultSuccess(map);
    }
    @GetMapping(value = "delMessage")
    public Map<String, Object> delMessage(@RequestParam(value = "id") int id) {
        Map<String, Object> map = new HashMap<>();
        messageService.deleteMessage(id);
        return ResultUtil.resultSuccess(map);
    }
}
