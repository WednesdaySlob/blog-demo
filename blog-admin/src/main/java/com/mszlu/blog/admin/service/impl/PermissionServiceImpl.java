package com.mszlu.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.admin.common.api.CommonResult;
import com.mszlu.blog.admin.mbg.domain.entity.Permission;
import com.mszlu.blog.admin.mbg.domain.vo.PageResult;
import com.mszlu.blog.admin.mbg.domain.vo.params.PageParam;
import com.mszlu.blog.admin.mbg.mapper.PermissionMapper;
import com.mszlu.blog.admin.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-05-18
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements
    PermissionService {

  private final PermissionMapper permissionMapper;

  @Override
  public CommonResult listPermission(PageParam pageParam) {
    // pageParam.getCurrentPage() 获取当前页码  表示用户想要查看第几页的数据 从 1 开始计数（第1页是1，不是0）
    // pageParam.getPageSize()  获取每页显示数量  每页大小
    Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
    LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
    if (StringUtils.isNotBlank(pageParam.getQueryString())) {
      // select count(*) from ms_permission where name = '用户管理'
      queryWrapper.eq(Permission::getName, pageParam.getQueryString());
    }
    // 执行分页查询
    Page<Permission> permissionPage = this.permissionMapper.selectPage(page, queryWrapper);
    PageResult<Permission> pageResult = new PageResult<>();
    // 设置数据列表
    pageResult.setList(permissionPage.getRecords());
    // 设置总记录数
    pageResult.setTotal(permissionPage.getTotal());
    return CommonResult.success(pageResult);
  }

  @Override
  public CommonResult add(Permission permission) {
    boolean code = this.permissionMapper.insert(permission) > 0;
    // 将插入后的对象 作为 data 返回
    if (code) {
      return CommonResult.success(permission.getId());
    } else {
      return CommonResult.fail("添加失败");
    }
  }

  @Override
  public CommonResult update(Permission permission) {
    this.permissionMapper.updateById(permission);
    return CommonResult.success(null);
  }

  @Override
  public CommonResult delete(Long id) {
    permissionMapper.deleteById(id);
    return CommonResult.success(null);
  }
}
