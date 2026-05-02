package com.mszlu.blog.controller;

import com.mszlu.blog.mbg.domain.vo.ArticleVO;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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
  public CommonResult articles(CommonPage commonPage){
    // ArticleVO 页面接收得数据
    List<ArticleVO> articles = articleService.listArticlesPage(commonPage);
    return CommonResult.success(articles);
  }

  @ApiOperation("最热文章")
  @PostMapping("hot")
  public CommonResult hotArticle(){
    int limit = 5;
    return articleService.hotArticle(limit);
  }


  @ApiOperation("首页  文章归档")
  @PostMapping("listArchives")
  public CommonResult listArticles(){
    return articleService.listArchives();
  }

  @ApiOperation("文章详情")
  @PostMapping("view/{id}")
  public CommonResult findArticleById(@PathVariable Long id){
    ArticleVO articleVO = articleService.findArticleById(id);
    return CommonResult.success(articleVO);
  }

}
