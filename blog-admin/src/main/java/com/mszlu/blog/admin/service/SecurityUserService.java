package com.mszlu.blog.admin.service;

import com.mszlu.blog.admin.mbg.domain.entity.Admin;
import com.mszlu.blog.admin.mbg.domain.entity.CustomUserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wx
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityUserService implements UserDetailsService {


  private final AdminService adminService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("开始加载用户: {}", username);

    Admin admin = adminService.findAdminByUserName(username)
        .orElseThrow(() -> {
          log.warn("用户不存在: {}", username);
          return new UsernameNotFoundException("用户名或密码错误");
        });

    return CustomUserDetails.builder()
        .username(admin.getUsername())
        .password(admin.getPassword())
        // 假设 1 表示启用
        .enabled(admin.getStatus() != null && admin.getStatus() == 1)
        // 账号没有过期
        .accountNonExpired(true)
        // 密码 未过期
        .credentialsNonExpired(true)
//    把数据库里的 role 转换成 Spring Security 认识的权限列表
        .authorities(getAuthorities(admin))
        .adminId(admin.getId())
        .build();
  }

  /**
   * 将 Admin 对象中的 role 转换为 GrantedAuthority 集合
   */

  private Collection<? extends GrantedAuthority> getAuthorities(Admin admin) {
    Set<GrantedAuthority> authorities = new HashSet<>();

    // 从数据库查询用户的角色和权限
    List<String> roles = adminService.findRolesByAdminId(admin.getId());
    List<String> permissions = adminService.findPermissionsByAdminId(admin.getId());

    // 添加角色
    roles.forEach(role ->
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

    // 添加权限
    permissions.forEach(permission ->
        authorities.add(new SimpleGrantedAuthority(permission)));

    return authorities;
  }
}
