package com.mszlu.blog.controller;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public CommonResult listCategory(){
    return categoryService.findAll();
  }

  @GetMapping("detail")
  @ApiOperation("查询所有的文章分类")
  public CommonResult categoriesDetail(){
    return categoryService.findAllDetail();
  }

  @GetMapping("detail/{id}")
  @ApiOperation("分类文章列表")
  public CommonResult categoriesDetailById(@PathVariable("id") Long id){
    return categoryService.categoriesDetailById(id);
  }

}
