package com.qy.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.service.base.exception.EduException;
import com.qy.service.ucenter.entity.RegisterVo;
import com.qy.service.ucenter.entity.UcenterMember;
import com.qy.service.ucenter.mapper.UcenterMemberMapper;
import com.qy.service.ucenter.service.UcenterMemberService;
import com.qy.service.ucenter.utils.MD5;
import com.qy.service.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper();
        wrapper.eq("openid", openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }

    //登录的方法
    @Override
    public String login(UcenterMember member) {
        //获取登录email和密码
        String email = member.getEmail();
        String password = member.getPassword();

        //email和密码非空判断
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new EduException(20001,"登录失败");
        }
        //判断email是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        UcenterMember emailMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(emailMember == null) {//没有这个email
            throw new EduException(20001,"登录失败email不存在");
        }
        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if(!MD5.encrypt(password).equals(emailMember.getPassword())) {
            throw new EduException(20001,"登录失败");
        }
        //判断用户是否禁用
        if(emailMember.getIsDisabled()) {
            throw new EduException(20001,"登录失败");
        }
        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(emailMember.getId(), emailMember.getNickname());
        return jwtToken;
    }

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String email = registerVo.getEmail(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码
        //非空判断
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new EduException(20001,"注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(redisCode)) {
            throw new EduException(20001,"注册失败验证码错误");
        }
        //判断email是否重复，表里面存在相同email不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new EduException(20001,"注册失败用户已存在");
        }
        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setEmail(email);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }
}
