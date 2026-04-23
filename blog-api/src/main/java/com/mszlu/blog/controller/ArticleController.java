package com.mszlu.blog.controller;

import com.mszlu.blog.VO.ArticleVO;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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


  @PostMapping
  public CommonResult articles(CommonPage commonPage){
    // ArticleVO 页面接收得数据
    List<ArticleVO> articles = articleService.listArticlesPage(commonPage);
    return CommonResult.success(articles);
  }

}
