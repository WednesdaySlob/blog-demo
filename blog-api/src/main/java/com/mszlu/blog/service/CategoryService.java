package com.mszlu.blog.service;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mszlu.blog.mbg.domain.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
public interface CategoryService extends IService<Category> {

  CategoryVO findCategoryById(Long id);

  CommonResult findAll();

  /**
   * 查询所有的文章分类
   * @return
   */
  CommonResult findAllDetail();

  /**
   * 分类文章列表
   * @param id
   * @return
   */
  CommonResult categoriesDetailById(Long id);
}
