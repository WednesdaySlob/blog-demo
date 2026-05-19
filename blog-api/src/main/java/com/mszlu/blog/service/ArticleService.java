package com.mszlu.blog.service;

import com.mszlu.blog.mbg.domain.vo.ArticleVO;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mszlu.blog.mbg.domain.vo.params.ArticleParam;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */

public interface ArticleService extends IService<Article> {

  /**
   * 文章列表分页查询
   * @param commonPage
   * @return
   */
 CommonResult listArticlesPage(CommonPage commonPage);


  CommonResult hotArticle(int limit);

  /**
   * 文章归档
   * @return
   */
  CommonResult listArchives();


  ArticleVO findArticleById(Long id);

  // 发布
  CommonResult publish(ArticleParam articleParam);
}
