package com.mszlu.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.mbg.domain.entity.Article;
import com.mszlu.blog.mbg.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 线程池使用
 *
 * @author wx
 */
@Component
public class ThreadService {


  @Async("taskExecutor")
  public void updateViewCount(ArticleMapper articleMapper, Article article) {
    Article articleUpdate = new Article();
    articleUpdate.setViewCounts(article.getViewCounts() - 1);
    LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Article::getId,article.getId()).eq(Article::getViewCounts,article.getViewCounts());
    articleMapper.update(articleUpdate,queryWrapper);
    try {
      // 睡眠5秒，证明不会影响主线程的使用
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
