package com.myf.weixin.mapper;

import com.myf.weixin.model.WeChatUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by maoyf0503 on 2018-4-10.
 *
 * @author maoyf0503
 */
@Mapper
public interface UserMapper {
    WeChatUser findByOpenId(String openid);
}
