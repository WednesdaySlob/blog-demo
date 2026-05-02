package com.mszlu.blog.service;

import com.mszlu.blog.mbg.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mszlu.blog.mbg.domain.vo.CategoryVO;

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

}
