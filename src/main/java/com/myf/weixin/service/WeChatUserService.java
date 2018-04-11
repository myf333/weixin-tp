package com.myf.weixin.service;

import com.myf.weixin.mapper.UserMapper;
import com.myf.weixin.model.WeChatUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maoyf0503 on 2018-4-11.
 *
 * @author maoyf0503
 */
@Service
public class WeChatUserService {
    @Autowired
    private UserMapper userMapper;

    public WeChatUser findByOpenId(String openid){
        return userMapper.findByOpenId(openid);
    }

    public int create(WeChatUser user){
        return userMapper.create(user);
    }

    public int update(WeChatUser user){
        return userMapper.update(user);
    }
}
