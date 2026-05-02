package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.mbg.domain.entity.ArticleBody;
import com.mszlu.blog.mbg.domain.vo.ArticleBodyVO;
import com.mszlu.blog.mbg.domain.vo.ArticleVO;
import com.mszlu.blog.mbg.domain.vo.CategoryVO;
import com.mszlu.blog.mbg.domain.vo.TagVO;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.dos.Archives;
import com.mszlu.blog.mbg.domain.entity.Article;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.mapper.ArticleBodyMapper;
import com.mszlu.blog.mbg.mapper.ArticleMapper;
import com.mszlu.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.service.ThreadService;
import com.mszlu.blog.service.UserService;
import com.mszlu.blog.service.TagService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements
    ArticleService {

  private final ArticleMapper articleMapper;
  private final UserService sysUserService;
  private final TagService tagService;
  private final CategoryService categoryService;
  private final ArticleBodyMapper articleBodyMapper;
  private final ThreadService threadService;

  @Override
  public List<ArticleVO> listArticlesPage(CommonPage commonPage) {
    QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
    Page<Article> page = new Page<>(commonPage.getPage(), commonPage.getPageSize());
    Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
    return copyList(articlePage.getRecords(), true, false, true,false);
  }

  @Override
  public CommonResult hotArticle(int limit) {
    LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper
        .orderByDesc(Article::getViewCounts)
        .select(Article::getId, Article::getTitle)
        .last("limit " + limit);
    List<Article> articles = articleMapper.selectList(queryWrapper);
    return CommonResult.success(copyList(articles, false, false, false,false));
  }

  @Override
  public CommonResult listArchives() {
    List<Archives> archivesList = articleMapper.listArchives();
    return CommonResult.success(archivesList);
  }

  @Override
  public ArticleVO findArticleById(Long id) {
    Article articles = articleMapper.selectById(id);
    threadService.updateViewCount(articleMapper,articles);
    return copy(articles,true,true,true,true);
  }

  private List<ArticleVO> copyList(List<Article> records, boolean isAuthor, boolean isBody,
      boolean isTags,boolean isCategory) {
    List<ArticleVO> articleVOList = new ArrayList<>();
    for (Article article : records) {
      ArticleVO articleVO = copy(article, isAuthor, isBody, isTags,isCategory);
      articleVOList.add(articleVO);
    }
    return articleVOList;
  }

  private ArticleVO copy(Article article, boolean isAuthor, boolean isBody, boolean isTags,boolean isCategory) {
    ArticleVO articleVO = new ArticleVO();
    BeanUtils.copyProperties(article, articleVO);
    articleVO.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    // 并不是 所有接口 都需要标签 作者信息

    if (isTags){
      Long articleId = article.getId();
      articleVO.setTags(tagService.findTagsByArticleId(articleId));
    }

    if (isAuthor) {
      SysUser sysUser = sysUserService.findSysUserById(article.getAuthorId());
      articleVO.setAuthor(sysUser.getNickname());
    }

    if (isBody){
      ArticleBodyVO articleBody  = findArticleBody(article.getId());
      articleVO.setBody(articleBody);
    }
    if (isCategory){
      CategoryVO categoryVO = findCategory(article.getCategoryId());
      articleVO.setCategory(categoryVO);
    }

    return articleVO;
  }

  private CategoryVO findCategory(Long categoryId) {
    return  categoryService.findCategoryById(categoryId);

  }

  private ArticleBodyVO findArticleBody(Long articleId) {
    LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ArticleBody::getArticleId,articleId);
    ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
    ArticleBodyVO articleBodyVO = new ArticleBodyVO();
    articleBodyVO.setContent(articleBody.getContent());
    return articleBodyVO;
  }

}
