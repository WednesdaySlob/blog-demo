package com.mszlu.blog.admin.controller;

import com.mszlu.blog.admin.common.api.CommonResult;
import com.mszlu.blog.admin.mbg.domain.entity.Permission;
import com.mszlu.blog.admin.mbg.domain.vo.params.PageParam;
import com.mszlu.blog.admin.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wx
 */
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

  private final PermissionService permissionService;

  @PostMapping("permission/permissionList")
  @ApiOperation("查询权限列表")
  public CommonResult permissionList(@RequestBody PageParam pageParam){
    return permissionService.listPermission(pageParam);
  }

  @PostMapping("permission/add")
  @ApiOperation("添加权限")
  public CommonResult add(@RequestBody Permission permission){
    return permissionService.add(permission);
  }

  @PostMapping("/permission/update")
  public CommonResult update(@RequestBody Permission permission){
    return permissionService.update(permission);
  }

  @GetMapping("permission/delete/{id}")
  public CommonResult delete(@PathVariable Long id){
    return permissionService.delete(id);
  }

}
