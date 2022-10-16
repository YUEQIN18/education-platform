package com.qy.service.ucenter.controller;

import com.google.gson.Gson;
import com.qy.service.base.exception.EduException;
import com.qy.service.ucenter.entity.UcenterMember;
import com.qy.service.ucenter.service.UcenterMemberService;
import com.qy.service.ucenter.utils.HttpClientUtils;
import com.qy.service.ucenter.utils.WechatUtils;
import com.qy.service.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sound.midi.Soundbank;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author qinyue
 * @create 2022-10-10 18:48:00
 */
@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WechatApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public String callback(String code, String state) {
        // 用code 可以得到access_token 和 open_id
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                WechatUtils.WX_OPEN_APP_ID,
                WechatUtils.WX_OPEN_APP_SECRET,
                code);
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
//            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new EduException(20001, "获取access_token失败");
        }
        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        //查询数据库当前用用户是否曾经使用过微信登录
        UcenterMember member = memberService.getByOpenid(openid);
        if (member == null) {
            System.out.println("新用户注册");
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new EduException(20001, "获取用户信息失败");
            }
            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo,
                    HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");
            //向数据库中插入一条记录
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }

    @GetMapping("login")
    public String getWechatQRCode(){
        //请求微信地址
        // %s相当于占位符
        String baseUrl =  "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = WechatUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new EduException(20001, e.getMessage());
        }
        String QRCodeUrl = String.format(
                baseUrl,
                WechatUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "Moke"
        );

        return "redirect:" + QRCodeUrl;
    }
}
