package com.mszlu.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mszlu.blog.admin.mbg.domain.entity.Admin;
import com.mszlu.blog.admin.mbg.mapper.AdminMapper;
import com.mszlu.blog.admin.service.AdminService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jdk.incubator.foreign.Addressable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wx
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {


  @Override
  public Optional<Admin> findAdminByUserName(String username) {
    Admin admin = lambdaQuery().eq(Admin::getUsername, username).one();
    // 如果查询了，就包装成optional 返回，没查到就返回 optional
    return Optional.ofNullable(admin);
  }

  @Override
  public List<String> findRolesByAdminId(Long id) {
    Admin admin = getById(id);
    if (admin == null) {
      return Collections.emptyList();
    }
    String roleStr = admin.getRole();
    if (roleStr == null || roleStr.trim().isEmpty()) {
      return Collections.emptyList();
    }

    return Arrays.stream(roleStr.split(",")).map(String::trim).collect(Collectors.toList());
  }

  @Override
  public List<String> findPermissionsByAdminId(Long id) {

    // 1. 先查  角色
    List<String> roles = findRolesByAdminId(id);
    // 2. 定义角色 -- 权限映射
    HashMap<String, List<String>> rolePermissionMap = new HashMap<>();
    /*
    权限标识符       含义                 能做什么
    user:*         用户模块的所有操作      增、删、改、查用户
    article:*      文章模块的所有操作      增、删、改、查文章
    system:*       系统模块的所有操作      系统设置、日志查看等
    article:view   只能查看文章           不能新增/修改/删除
    comment:add    只能添加评论           不能删除/修改评论
     */
    rolePermissionMap.put("ROLE_ADMIN", Arrays.asList("user:*", "article:*", "system:*"));
    rolePermissionMap.put("ROLE_USER", Arrays.asList("article:view", "comment:add"));

    // 3. 收集 权限
    HashSet<String> permissions = new HashSet<>();
    for (String role : roles) {
      List<String> perms = rolePermissionMap.get(role);
      if (perms != null) {
        permissions.addAll(perms);
      }
    }
    return new ArrayList<>(permissions);
  }
}
