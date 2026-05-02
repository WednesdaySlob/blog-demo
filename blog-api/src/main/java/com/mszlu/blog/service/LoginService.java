package com.mszlu.blog.service;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.domain.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录服务接口
 * @author wx
 */
@Transactional
public interface LoginService {

  /**
   * 登录
   * @param loginParam
   * @return
   */
  CommonResult login(LoginParam loginParam);

  /**
   * 退出登录
   * @param token
   * @return
   */
  CommonResult logout(String token);

  SysUser checkToken(String token);
}
