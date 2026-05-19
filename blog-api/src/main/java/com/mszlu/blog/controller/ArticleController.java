package com.mszlu.blog.controller;

import com.mszlu.blog.common.aop.LogAnnotation;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.common.cache.Cache;
import com.mszlu.blog.mbg.domain.vo.ArticleVO;
import com.mszlu.blog.mbg.domain.vo.params.ArticleParam;
import com.mszlu.blog.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {


  private final ArticleService articleService;


  @ApiOperation("文章列表")
  @PostMapping
  @LogAnnotation(module = "文章", operator = "获取文章列表")
  public CommonResult articles(@RequestBody(required = false) CommonPage commonPage) {
    // 判空处理
    if (commonPage == null){
      commonPage = new CommonPage();
    }
    // 防止 前端传入 负数或 0
    if (commonPage.getPage() == null || commonPage.getPage() < 1){
      commonPage.setPage(1);
    }
    if (commonPage.getPageSize() == null || commonPage.getPageSize() < 1){
      commonPage.setPageSize(10);
    }
    // ArticleVO 页面接收得数据
    return articleService.listArticlesPage(commonPage);
  }

  @ApiOperation("最热文章")
  @PostMapping("hot")
  @Cache(expire = 5 * 60 * 1000,name = "hot_article")
  public CommonResult hotArticle() {
    int limit = 5;
    return articleService.hotArticle(limit);
  }


  @ApiOperation("首页  文章归档")
  @PostMapping("listArchives")
  public CommonResult listArticles() {
    return articleService.listArchives();
  }

  @ApiOperation("文章详情")
  @PostMapping("view/{id}")
  public CommonResult findArticleById(@PathVariable Long id) {
    ArticleVO articleVO = articleService.findArticleById(id);
    return CommonResult.success(articleVO);
  }

  @PostMapping("publish")
  @ApiOperation("文章发布")
  public CommonResult publish(@RequestBody ArticleParam articleParam) {
    return articleService.publish(articleParam);
  }

}
