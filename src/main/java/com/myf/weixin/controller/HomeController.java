package com.myf.weixin.controller;

import com.myf.weixin.mapper.UserMapper;
import com.myf.weixin.model.WeChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by maoyf0503 on 2018-4-10.
 *
 * @author maoyf0503
 */
@RestController
public class HomeController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startLocalDate = LocalDateTime.now();
        return sf.format(startLocalDate);
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public WeChatUser user(){
        WeChatUser user = userMapper.findByOpenId("dsdsd");
        return user;
    }
}
