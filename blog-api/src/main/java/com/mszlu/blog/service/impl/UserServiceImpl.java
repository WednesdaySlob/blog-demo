package com.mszlu.blog.service.impl;

import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.mapper.SysUserMapper;
import com.mszlu.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements UserService {

  private final SysUserMapper sysUserMapper;



  @Override
  public SysUser findSysUserById(Long userid) {

    SysUser sysUser = sysUserMapper.selectById(userid);
    if (sysUser == null){
      sysUser = new SysUser();
      sysUser.setNickname("码神之路");
    }
    return sysUser;
  }
}
