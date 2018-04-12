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
public class JsAPISign {
    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;

    public JsAPISign(String appId, String timestamp, String nonceStr, String signature) {
        this.appId = appId;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signature = signature;
    }
}
