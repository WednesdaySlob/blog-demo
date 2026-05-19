package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.entity.Category;
import com.mszlu.blog.mbg.domain.vo.CategoryVO;
import com.mszlu.blog.mbg.mapper.CategoryMapper;
import com.mszlu.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event.ID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements
    CategoryService {

  private final CategoryMapper categoryMapper;

  @Override
  public CategoryVO findCategoryById(Long id) {
    Category category = categoryMapper.selectById(id);
    CategoryVO categoryVO = new CategoryVO();
    BeanUtils.copyProperties(category, categoryVO);
    return categoryVO;
  }

  @Override
  public CommonResult findAll() {
    List<Category> categories = this.categoryMapper.selectList(new LambdaQueryWrapper<>());
//    return null;
    return CommonResult.success(copyList(categories));
  }

  @Override
  public CommonResult findAllDetail() {
    List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
    // 页面交互的对象
    return CommonResult.success(copyList(categories));
  }

  @Override
  public CommonResult categoriesDetailById(Long id) {
    Category category = categoryMapper.selectById(id);
    CategoryVO categoryVO = copy(category);
    return CommonResult.success(categoryVO);
  }

  public List<CategoryVO> copyList(List<Category> categorieList) {

    List<CategoryVO> categoryVOlist = new ArrayList<>();
    for (Category category : categorieList) {
      categoryVOlist.add(copy(category));
    }
    return categoryVOlist;

  }

  public CategoryVO copy(Category category) {
    CategoryVO categoryVO = new CategoryVO();
    BeanUtils.copyProperties(category, categoryVO);
    return categoryVO;
  }

}
