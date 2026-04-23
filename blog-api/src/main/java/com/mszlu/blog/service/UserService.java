package com.mszlu.blog.service;

import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
