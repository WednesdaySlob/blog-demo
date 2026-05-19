package com.mszlu.blog.admin.service;

import com.mszlu.blog.admin.mbg.domain.entity.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminService {


  /**
   * 根据 username 查询 用户信息
   * @param username  用户名
   * @return
   */
  Optional<Admin> findAdminByUserName(String username);

  /**
   * 根据 用户的 roles
   * @param id
   * @return
   */
  List<String> findRolesByAdminId(Long id);

  /**
   * 查询 用户权限
   * @param id
   * @return
   */
  List<String> findPermissionsByAdminId(Long id);
}
