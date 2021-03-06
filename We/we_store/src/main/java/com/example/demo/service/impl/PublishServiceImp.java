package com.example.demo.service.impl;

import com.example.demo.mapper.PublishMapper;
import com.example.demo.model.book.Publish;
import com.example.demo.service.PublishService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("firstPublish")
public class PublishServiceImp implements PublishService {

    @Autowired
    PublishMapper publishMapper;

    @Override
    public int addPublish(Publish publish) {
        int result = this.publishMapper.addPublish(publish);
        return result;
    }

    @Override
    public int deletePublish(int id) {
        int result = publishMapper.deletePublish(id);
        return result;
    }

    @Override
    public int modifyPublish(Publish publish) {
        int result = publishMapper.modifyPublish(publish);
        return result;
    }

    @Override
    public int modifyIsShow(int id) {
        int result = publishMapper.modifyIsShow(id);
        return result;
    }
//
    @Override
    public int getPublishCount() {
        return publishMapper.getPublishCount();
    }

    @Override
    public List<Publish> getPublishByPage(int page, int pageSize) {
        int start = (page-1)*pageSize;
        System.out.println(start+":"+pageSize);
        return publishMapper.getPublishByPage(start,pageSize);
    }

    @Override
    public List<String> getPublishNames() {
        return publishMapper.getPublishNames();
    }

    @Override
    public Publish getPublishById(int id) {
        return publishMapper.getPublishById(id);
    }
//
//    @Override
//    public Publish getPublishByName(String name) {
//        return publishMapper.getPublishByName(name);
//    }
}
