package com.myf.weixin.controller;

import com.myf.weixin.model.*;
import com.myf.weixin.service.OauthService;
import com.myf.weixin.service.WeChatUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * Created by maoyf0503 on 2018-4-11.
 *
 * @author maoyf0503
 */
@Controller
@RequestMapping(value = "oauth")
public class WeiXinController {
    private static Logger logger = LoggerFactory.getLogger(WeiXinController.class);

    @Autowired
    private OauthService oauthService;
    @Autowired
    private WXConfig wxConfig;
    @Autowired
    private WeChatUserService userService;

    @RequestMapping(value = "oauth")
    public String oauth(String redirectUrl)throws Exception{
        String grantBackUrl = "http://www.codingheart.net/weixin/oauth/grantBack";
        logger.info(wxConfig.getAppId());
        String url = oauthService.getAuthorizeUrl(wxConfig.getAppId(), URLEncoder.encode(grantBackUrl, "UTF-8"), Scope.snsapi_userinfo, URLEncoder.encode(redirectUrl, "UTF-8"));
        return MessageFormat.format("redirect:{0}",url);
    }

    @RequestMapping(value = "grantBack")
    public String grantBack(String code,String state,RedirectAttributes model) throws Exception{
        String url = URLDecoder.decode(state,"UTF-8");
        if(null != code && !"".equals(code)) {
            OAuthAccessTokenResult result = oauthService.getAccessToken(wxConfig.getAppId(), wxConfig.getAppSecret(), code);
            if (result.getErrcode() == 0) {
                GrantUserInfoRet ret = oauthService.getUserInfo(result.getAccess_token(),result.getOpenid());
                if(ret.getErrcode() == 0){
                    WeChatUser user = userService.findByOpenId(ret.getOpenid());
                    boolean isAdd = false;
                    if(user == null)
                    {
                        user = new WeChatUser();
                        user.setOpenid(ret.getOpenid());
                        user.setUnionid(ret.getUnionid());
                        isAdd = true;
                    }
                    user.setCity(ret.getCity());
                    user.setCountry(ret.getCountry());
                    user.setHeadimgurl(ret.getHeadimgurl());
                    user.setNickname(ret.getNickname());
                    if(ret.getPrivilege()!=null) {
                        user.setPrivilege(StringUtils.join(ret.getPrivilege().toArray()));
                    }
                    user.setProvince(ret.getProvince());
                    user.setSex(Integer.parseInt(ret.getSex()));
                    if(isAdd){
                        userService.create(user);
                    }else {
                        userService.update(user);
                    }
                    model.addAttribute("token",ret.getOpenid());
                }
            }
        }
        return MessageFormat.format("redirect:{0}",url);
    }

    @RequestMapping(value = "grant",method = RequestMethod.GET)
    public String grant(){
        return "grant_test";
    }
}
