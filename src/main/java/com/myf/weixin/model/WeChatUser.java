package com.myf.weixin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Created by maoyf0503 on 2018-4-10.
 *
 * @author maoyf0503
 */
@Data
@NoArgsConstructor
public class WeChatUser {
    private String openid;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String privilege;
    private String unionid;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
