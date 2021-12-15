package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.book.Publish;
import com.example.demo.model.user.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface PublishMapper extends BaseMapper<Publish> {
    int addPublish(Publish publish);
    int deletePublish(int id);
    int modifyPublish(Publish publish);
    int modifyIsShow(int id);
    int getPublishCount();
    List<Publish> getPublishByPage(@Param("page")int page, @Param("pageSize")int pageSize);
    List<String> getPublishNames();
    Publish getPublishById(int id);
//    Publish getPublishByName(String name);
}
