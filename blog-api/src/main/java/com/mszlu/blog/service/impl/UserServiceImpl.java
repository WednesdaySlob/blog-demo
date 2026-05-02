package com.mszlu.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.common.utils.JWTUtils;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.domain.vo.ErrorCode;
import com.mszlu.blog.mbg.domain.vo.LoginUserVo;
import com.mszlu.blog.mbg.domain.vo.UserVO;
import com.mszlu.blog.mbg.mapper.SysUserMapper;
import com.mszlu.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

  private final SysUserMapper sysUserMapper;

  private final JWTUtils jwtUtils;

  private final StringRedisTemplate stringRedisTemplate;

  @Override
  public SysUser findSysUserById(Long userid) {

    SysUser sysUser = sysUserMapper.selectById(userid);
    if (sysUser == null) {
      sysUser = new SysUser();
      sysUser.setNickname("码神之路");
    }
    return sysUser;
  }

  @Override
  public SysUser findUser(String account, String pwd) {
    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysUser::getAccount, account)
        .eq(SysUser::getPassword, pwd)
        .select(SysUser::getId, SysUser::getAccount, SysUser::getNickname)
        .last("limit 1");
    return sysUserMapper.selectOne(queryWrapper);
  }

  @Override
  public CommonResult getUserInfoByToken(String token) {
    Map<String, Object> map = JWTUtils.checkToken(token, jwtUtils.getSecretKey());
    if (map == null) {
      return CommonResult.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
    }
    String userJson = stringRedisTemplate.opsForValue().get("TOKEN_" + token);
    if (StringUtils.isBlank(userJson)) {
      return CommonResult.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
    }

    SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
    LoginUserVo loginUserVo = new LoginUserVo();
    loginUserVo.setAccount(sysUser.getAccount());
    loginUserVo.setId(sysUser.getId());
    loginUserVo.setAvatar(sysUser.getAvatar());
    loginUserVo.setNickname(sysUser.getNickname());

    return CommonResult.success(loginUserVo);
  }

  @Override
  public UserVO findUserVoById(Long id) {
    SysUser sysUser = sysUserMapper.selectById(id);
    if (sysUser == null) {
      sysUser = new SysUser();
      sysUser.setId(1L);
      sysUser.setAvatar("/static/img/logo.b3a48c0.png");
      sysUser.setNickname("码神之路");
    }
    UserVO userVO = new UserVO();
    userVO.setAvatar(sysUser.getAvatar());
    userVO.setNickname(sysUser.getNickname());
    userVO.setId(sysUser.getId());
    return userVO;
  }

  @Override
  public SysUser findUserByAccount(String account) {
    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysUser::getAccount, account)
        .last("limit 1");
    return sysUserMapper.selectOne(queryWrapper);
  }


}
