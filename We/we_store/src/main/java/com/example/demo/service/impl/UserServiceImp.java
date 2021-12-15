package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.user.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XuShu
 * @since 2021-12-01
 */
@Service("firstUser")
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(String account) {
        return userMapper.getUser(account);
    }

    @Override
    public int addUser(User user) {
        int result = userMapper.addUser(user);
        return result;
    }

    @Override
    public void addInfo(String account,String bookName) {
        String info = "您好，您订阅的"+bookName+"已重新上架!";
        System.out.println(info);
        System.out.println(account);
        userMapper.addInfo(account,info);
    }

}
