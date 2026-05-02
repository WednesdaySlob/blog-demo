package com.mszlu.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.common.utils.JWTUtils;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.domain.vo.ErrorCode;
import com.mszlu.blog.mbg.domain.vo.params.RegisterParam;
import com.mszlu.blog.service.RegisterService;
import com.mszlu.blog.service.UserService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wx
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserService userService;
  private final JWTUtils jWTUtils;
  private final RedisTemplate redisTemplate;

  @Override
  public CommonResult register(RegisterParam registerParam) {
    String account = registerParam.getAccount();
    String password = registerParam.getPassword();
    String nickname = registerParam.getNickname();
    if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(
        nickname)) {
      return CommonResult.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
    }
    SysUser sysUser = this.userService.findUserByAccount(account);
    if (sysUser != null) {
      return CommonResult.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
    }
    sysUser = new SysUser();
    sysUser.setNickname(nickname);
    sysUser.setAccount(account);
    sysUser.setPassword(password);
    sysUser.setCreateDate(System.currentTimeMillis());
    sysUser.setLastLogin(System.currentTimeMillis());
    sysUser.setAvatar("/static/img/logo.b3a48c0.png");
    // 1 为true
    sysUser.setAdmin(1);
    // 0 为 false
    sysUser.setDeleted(0);
    sysUser.setSalt("");
    sysUser.setStatus("");
    sysUser.setEmail("");
    this.userService.save(sysUser);

    String token = jWTUtils.createToken(sysUser.getId(), jWTUtils.getSecretKey());
    redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
    return CommonResult.success(token);
  }
}
