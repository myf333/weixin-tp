package com.myf.weixin.controller;

import com.myf.weixin.model.JsAPISign;
import com.myf.weixin.model.WXConfig;
import com.myf.weixin.model.WxJsonResult;
import com.myf.weixin.service.TokenService;
import com.myf.weixin.util.CryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * Created by maoyf0503 on 2018-4-12.
 *
 * @author maoyf0503
 */
@Controller
@RequestMapping(value = "token")
public class TokenController {
    private static Logger logger = LoggerFactory.getLogger(TokenController.class);
    @Autowired
    private WXConfig wxConfig;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "config",method = RequestMethod.GET)
    @ResponseBody
    public JsAPISign getConfig(String signUrl) throws Exception{
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String nonceStr =  UUID.randomUUID().toString();
        String jsApiTicket = tokenService.GetJsapiTicket(wxConfig.getAppId(), wxConfig.getAppSecret());

        String signString = MessageFormat.format("jsapi_ticket={0}&noncestr={1}&timestamp={2}&url={3}", jsApiTicket, nonceStr, timestamp, signUrl);
        String sign = CryptUtil.encrypt(signString,"SHA-1");
        return new JsAPISign(wxConfig.getAppId(),timestamp,nonceStr,sign);
    }

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(){
        return "js_test";
    }
}
