package com.example.demo.controller;


import com.example.demo.model.user.Message;
import com.example.demo.model.user.User;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
import com.example.demo.util.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "LoginController")
@RestController
public class UserController {
//    @Autowired
//    private UserService userService;
//    @ApiOperation(value = "登录之后返回token")
//    @PostMapping("/login")
//    public ResultUtil login(SecurityUser securityUser, HttpServletRequest request) {
//        return userService.login(securityUser.getUsername(),securityUser.getPassword(),request);
//    }

    @Autowired
    @Qualifier("firstUser")
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    MessageService messageService;
    /**
     * 用户注册
     * @param account
     * @param password
     * @return
     */
    @GetMapping(value = "/user/register")
    @ResponseBody
    public Map<String, Object> registerUser(@RequestParam(value = "account") String account,
                                            @RequestParam(value = "password") String password){
        System.out.println("======开始注册=======");
        User user1 = userService.getUser(account);
        if(user1 != null){
            System.out.println("该账号已经被注册！");
            return ResultUtil.resultCode(500,"该账号已被注册！");
        }
        System.out.println("该账号未被注册");
        User user = new User();
        user.setAccount(account);
        user.setPassword(passwordEncoder.encode(password));
        user.setManage(false);
        user.setEnable(true);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Message message = new Message();
        message.setIsRead(false);
        message.setDate(timestamp);
        message.setAccount(account);
        String str = "您好，感谢您注册成为WE书屋用户～";
        message.setMessage(str);
        messageService.addMessage(message);
        if(userService.addUser(user)>0){
            return ResultUtil.resultCode(200,"注册成功");
        }
        return ResultUtil.resultCode(500,"注册失败");
    }
    /**
     * 得到一个用户的个人信息
     * @param account
     * @return
     */
    @ResponseBody
    @GetMapping("/getUserInfo")
    public Map<String,Object> getUserInfo(@RequestParam(value = "account")String account){
        User user = userService.getUser(account);
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        return ResultUtil.resultSuccess(map);
    }

}

