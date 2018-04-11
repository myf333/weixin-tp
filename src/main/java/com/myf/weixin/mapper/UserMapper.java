package com.myf.weixin.mapper;

import com.myf.weixin.model.WeChatUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by maoyf0503 on 2018-4-10.
 *
 * @author maoyf0503
 */
@Component
@Mapper
public interface UserMapper {
    WeChatUser findByOpenId(String openid);
    int create(WeChatUser user);
    int update(WeChatUser user);
}
