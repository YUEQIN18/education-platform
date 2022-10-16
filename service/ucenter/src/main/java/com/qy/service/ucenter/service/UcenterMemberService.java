package com.qy.service.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.service.ucenter.entity.RegisterVo;
import com.qy.service.ucenter.entity.UcenterMember;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    UcenterMember getByOpenid(String openid);

    //登录的方法
    String login(UcenterMember member);

    //注册的方法
    void register(RegisterVo registerVo);
}
