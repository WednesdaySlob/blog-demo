package com.mszlu.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.common.utils.JWTUtils;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.domain.vo.ErrorCode;
import com.mszlu.blog.mbg.domain.vo.params.LoginParam;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 登录实现类
 *
 * @author wx
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private static final String slat = "mszlu!@#";

  private final UserService userService;

  private final JWTUtils jwtUtils;

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public CommonResult login(LoginParam loginParam) {
    String account = loginParam.getAccount();
    String password = loginParam.getPassword();
    if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
      return CommonResult.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
    }
    String pwd = DigestUtils.md2Hex(password + slat);
    SysUser user = userService.findUser(account, pwd);
    if (user == null) {
      return CommonResult.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),
          ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
    }

    String token = jwtUtils.createToken(user.getId(), jwtUtils.getSecretKey());
    redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
    return CommonResult.success(token);

  }

  @Override
  public CommonResult logout(String token) {
    redisTemplate.delete("TOKEN_" + token);
    return CommonResult.success(null);
  }

  @Override
  public SysUser checkToken(String token) {
    // 解析token -> 提取用户Id -> 查询数据库 -> 返回SysUser 对象
    if (StringUtils.isBlank(token)) {
      throw new RuntimeException("Token 不能为空");
    }
    try {
      // 解析JWT Token，校验签名和有效期
      Jws<Claims> jws = Jwts.parserBuilder()
          .build()
          .parseClaimsJws(token);
      // 获取 payload
      // 取 payload（载荷），里面是你的自定义字段（如 userId、username 等）
      Claims claims = jws.getBody();

      // 假设你的 token 中存了 userId（比如 "userId": 1001）
      // 或者 claims.get("userId").toString()
      Long userId = Long.parseLong(claims.getSubject());

      // 从数据库查询用户
      // 返回 SysUser 对象
      return userService.getById(userId);

    } catch (ExpiredJwtException e) {
      throw new RuntimeException("Token 已过期");
    } catch (SignatureException | MalformedJwtException e) {
      throw new RuntimeException("Token 无效");
    } catch (Exception e) {
      throw new RuntimeException("Token 解析失败");
    }
  }
}
