package com.myf.weixin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by maoyf0503 on 2018-4-11.
 *
 * @author maoyf0503
 */
@Data
@NoArgsConstructor
public class WXConfig {
    @Value("appId")
    private String appId;
    @Value("appSecret")
    private String appSecret;
}
