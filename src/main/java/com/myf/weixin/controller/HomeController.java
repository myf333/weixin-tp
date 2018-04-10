package com.myf.weixin.controller;

import com.myf.weixin.mapper.UserMapper;
import com.myf.weixin.model.WeChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        WeChatUser user = userMapper.findByOpenId("121111");
        return "hello1";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public WeChatUser user(){
        WeChatUser user = userMapper.findByOpenId("dsdsd");
        return user;
    }
}
