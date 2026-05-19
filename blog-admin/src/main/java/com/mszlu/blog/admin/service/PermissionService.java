package com.mszlu.blog.admin.service;

import com.mszlu.blog.admin.common.api.CommonResult;
import com.mszlu.blog.admin.mbg.domain.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mszlu.blog.admin.mbg.domain.vo.params.PageParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2026-05-18
 */
public interface PermissionService extends IService<Permission> {

  /**
   * 权限列表
   * @param pageParam
   * @return
   */
  CommonResult listPermission(PageParam pageParam);

  /**
   * 添加权限
   * @param permission
   * @return
   */
  CommonResult add(Permission permission);


  /**
   * 更新权限
   * @param permission
   * @return
   */
  CommonResult update(Permission permission);

  /**
   * 删除 权限
   * @param id
   * @return
   */
  CommonResult delete(Long id);
}
