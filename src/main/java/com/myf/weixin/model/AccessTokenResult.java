package com.myf.weixin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by maoyf0503 on 2018-4-12.
 *
 * @author maoyf0503
 */
@Data
@NoArgsConstructor
public class AccessTokenResult extends WxJsonResult {
    /**
     * 获取到的凭证
     * */
    private String access_token;
    /**
     * 凭证有效时间，单位：秒
     * */
    private long expires_in;
}
