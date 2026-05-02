package com.mszlu.blog.service;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mszlu.blog.mbg.domain.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
public interface UserService extends IService<SysUser> {

  SysUser findSysUserById(Long userId);

  SysUser findUser(String account, String pwd);

  CommonResult getUserInfoByToken(String token);

  UserVO findUserVoById(Long id);

  SysUser findUserByAccount(String account);


}
