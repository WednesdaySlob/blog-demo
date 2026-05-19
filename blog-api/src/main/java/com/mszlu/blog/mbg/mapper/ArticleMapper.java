package com.mszlu.blog.mbg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.mbg.domain.dos.Archives;
import com.mszlu.blog.mbg.domain.entity.Article;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
public interface ArticleMapper extends BaseMapper<Article> {

  List<Archives> listArchives();


  IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
