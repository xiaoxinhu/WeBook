package com.example.demo.mapper;


//import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    int addUser(User user);//添加用户
    User getUser(String account);//通过账号得到用户

    void addInfo(@Param("account") String account,@Param("info") String info);
}
