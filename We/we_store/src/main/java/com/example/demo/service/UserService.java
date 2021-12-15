package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XuShu
 * @since 2021-12-01
 */

@Service
public interface UserService extends IService<User> {
//    /**
//     * 登陆之后返回Token
//     * @param username
//     * @param password
//     * @param request
//     * @return
//     */
//    ResultUtil login(String username, String password, HttpServletRequest request);
    User getUser(String account);
    int addUser(User user);

    void addInfo(String account,String bookName);
}
