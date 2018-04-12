package com.myf.weixin.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myf.weixin.model.AccessTokenResult;
import com.myf.weixin.model.JsAPITicketResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by maoyf0503 on 2018-4-12.
 *
 * @author maoyf0503
 */
@Service
public class TokenService {
    @Autowired
    private HttpRequestService httpRequestService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public String GetAccessToken(String appId, String appSecret) throws Exception{
        String key = String.format("%s:accessToken", appId);
        String accessToken = redisTemplate.opsForValue().get(key);
        if("".equals(accessToken)||null==accessToken||"null".equals(accessToken)){
            String url = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}",appId,appSecret);
            String res = httpRequestService.get(url);
            if(!"".equals(res)) {
                Gson gson = new GsonBuilder().create();
                AccessTokenResult ret = gson.fromJson(res, AccessTokenResult.class);
                if(ret.getErrcode() == 0){
                    accessToken = ret.getAccess_token();
                    redisTemplate.opsForValue().set(key,accessToken);
                    redisTemplate.expire(key,ret.getExpires_in(), TimeUnit.SECONDS);
                }
            }
        }
        return accessToken;
    }

    public String GetJsapiTicket(String appId, String appSecret) throws Exception{
        String key = String.format("%s:jsapiticket", appId);
        String jsApiTicket = redisTemplate.opsForValue().get(key);
        if("".equals(jsApiTicket)||null==jsApiTicket||"null".equals(jsApiTicket)){
            String accessToken = GetAccessToken(appId,appSecret);
            JsAPITicketResult ret = getTicket(accessToken, "jsapi");
            if(ret.getErrcode() == 0){
                jsApiTicket = ret.getTicket();
                redisTemplate.opsForValue().set(key,jsApiTicket);
                redisTemplate.expire(key,ret.getExpires_in(), TimeUnit.SECONDS);
            }
        }
        return jsApiTicket;
    }

    public String GetCardApiTicket(String appId, String appSecret) throws Exception{
        String key = String.format("%s:cardapiticket", appId);
        String jsApiTicket = redisTemplate.opsForValue().get(key);
        if("".equals(jsApiTicket)||null==jsApiTicket||"null".equals(jsApiTicket)){
            String accessToken = GetAccessToken(appId,appSecret);
            JsAPITicketResult ret = getTicket(accessToken, "wx_card");
            if(ret.getErrcode() == 0){
                jsApiTicket = ret.getTicket();
                redisTemplate.opsForValue().set(key,jsApiTicket);
                redisTemplate.expire(key,ret.getExpires_in(), TimeUnit.SECONDS);
            }
        }
        return jsApiTicket;
    }

    private JsAPITicketResult getTicket(String accessToken,String type) throws Exception{
        String url = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type={1}",accessToken,type);
        Gson gson = new Gson();
        String res = httpRequestService.get(url);
        return gson.fromJson(res,JsAPITicketResult.class);
    }
}
