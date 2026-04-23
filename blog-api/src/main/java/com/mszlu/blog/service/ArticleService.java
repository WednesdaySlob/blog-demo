package com.mszlu.blog.service;

import com.mszlu.blog.VO.ArticleVO;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.mbg.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
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

  List<ArticleVO> listArticlesPage(CommonPage commonPage);
}
