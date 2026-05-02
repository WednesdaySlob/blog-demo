package com.mszlu.blog.service.impl;

import com.mszlu.blog.mbg.domain.entity.Category;
import com.mszlu.blog.mbg.domain.vo.CategoryVO;
import com.mszlu.blog.mbg.mapper.CategoryMapper;
import com.mszlu.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event.ID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

  private final CategoryMapper categoryMapper;

  @Override
  public CategoryVO findCategoryById(Long id) {
    Category category = categoryMapper.selectById(id);
    CategoryVO categoryVO = new CategoryVO();
    BeanUtils.copyProperties(category,categoryVO);
    return categoryVO;
  }
}
