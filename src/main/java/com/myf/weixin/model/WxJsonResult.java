package com.myf.weixin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by maoyf0503 on 2018-4-11.
 *
 * @author maoyf0503
 */
@Data
@NoArgsConstructor
public class WxJsonResult {
    private int errcode;
    private String errmsg;
}
