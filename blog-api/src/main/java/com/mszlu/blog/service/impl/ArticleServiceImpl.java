package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.common.utils.UserThreadLocal;
import com.mszlu.blog.mbg.domain.entity.ArticleBody;
import com.mszlu.blog.mbg.domain.entity.ArticleTag;
import com.mszlu.blog.mbg.domain.vo.ArticleBodyVO;
import com.mszlu.blog.mbg.domain.vo.ArticleVO;
import com.mszlu.blog.mbg.domain.vo.CategoryVO;
import com.mszlu.blog.mbg.domain.vo.TagVO;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.dos.Archives;
import com.mszlu.blog.mbg.domain.entity.Article;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.domain.vo.params.ArticleParam;
import com.mszlu.blog.mbg.mapper.ArticleBodyMapper;
import com.mszlu.blog.mbg.mapper.ArticleMapper;
import com.mszlu.blog.mbg.mapper.ArticleTagMapper;
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
import org.springframework.transaction.annotation.Transactional;

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
  private final ArticleTagMapper articleTagMapper;

//  @Override
//  public CommonResult listArticlesPage(CommonPage commonPage) {
//    // 1. 分页查询 article 数据库表
//    LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//    Page<Article> page = new Page<>(commonPage.getPage(), commonPage.getPageSize());
//    if (commonPage.getCategoryId() != null) {
//      // and category_id =#{categoryId}
//      queryWrapper.eq(Article::getCategoryId, commonPage.getCategoryId());
//    }
//
//   List<Long> articleIdList = new ArrayList<>();
//    if (commonPage.getTagId() != null){
//      LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//      articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,commonPage.getTagId());
//      List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//      for (ArticleTag articleTag : articleTags) {
//        articleIdList.add(articleTag.getArticleId());
//      }
//      if (!articleIdList.isEmpty()){
//        queryWrapper.in(Article::getId,articleIdList);
//      }
//    }
//
//    // 是否置顶进行排序
//    // order by create_date desc
//    queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//    Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//    List<Article> records = articlePage.getRecords();
//    // 能直接返回？ 不能
//    List<ArticleVO> articleVOList = copyList(records, true, true);
//    return CommonResult.success(articleVOList);
////    return copyList(articlePage.getRecords(), true, false, true, false);
//  }

  @Override
  public CommonResult listArticlesPage(CommonPage commonPage) {
    Page<Article> page = new Page<>(commonPage.getPage(), commonPage.getPageSize());
    IPage<Article> articleIPage = this.articleMapper.listArticle(page,commonPage.getCategoryId(),commonPage.getTagId(),commonPage.getYear(),commonPage.getMonth());
    List<Article> records = articleIPage.getRecords();
    return CommonResult.success(copyList(records,true,true));
  }

  @Override
  public CommonResult hotArticle(int limit) {
    LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper
        .orderByDesc(Article::getViewCounts)
        .select(Article::getId, Article::getTitle)
        .last("limit " + limit);
    List<Article> articles = articleMapper.selectList(queryWrapper);
    return CommonResult.success(copyList(articles, false, false, false, false));
  }

  @Override
  public CommonResult listArchives() {

    List<Archives> archivesList = articleMapper.listArchives();
    return CommonResult.success(archivesList);
  }

  @Override
  public ArticleVO findArticleById(Long id) {
    Article articles = articleMapper.selectById(id);
    threadService.updateViewCount(articleMapper, articles);
    return copy(articles, true, true, true, true);
  }

  @Override
  @Transactional
  public CommonResult publish(ArticleParam articleParam) {
    SysUser sysUser = UserThreadLocal.get();
    Article article = new Article();
    article.setAuthorId(sysUser.getId());
    article.setCategoryId(articleParam.getCategory().getId());
    article.setCreateDate(System.currentTimeMillis());
    article.setCommentCounts(0);
    article.setSummary(articleParam.getSummary());
    article.setTitle(articleParam.getTitle());
    article.setViewCounts(0);
    article.setWeight(Article.Article_Common);
    article.setBodyId(-1L);
    this.articleMapper.insert(article);

    // tags
    List<TagVO> tags = articleParam.getTags();
    if (tags != null) {
      for (TagVO tag : tags) {
        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(article.getId());
        articleTag.setTagId(tag.getId());
        articleTagMapper.insert(articleTag);
      }
    }
    ArticleBody articleBody = new ArticleBody();
    articleBody.setContent(articleParam.getBody().getContent());
    articleBody.setContentHtml(articleParam.getBody().getContentHtml());
    articleBody.setArticleId(article.getId());
    articleBodyMapper.insert(articleBody);

    article.setBodyId(articleBody.getId());
    articleMapper.updateById(article);
    ArticleVO articleVO = new ArticleVO();
    articleVO.setId(article.getId());

    return CommonResult.success(articleVO);
  }

  public List<ArticleVO> copyList(List<Article> records, boolean isTag,boolean isAuthor){
   List<ArticleVO> articleVOList = new ArrayList<>();
    for (Article record : records) {
      articleVOList.add(copy(record,isTag,isAuthor,false,false));
    }
    return articleVOList;
  }

  private List<ArticleVO> copyList(List<Article> records, boolean isAuthor, boolean isBody,
      boolean isTags, boolean isCategory) {
    List<ArticleVO> articleVoList = new ArrayList<>();

    for (Article record : records) {
      // 将每个 Article 对象转换为 ArticleVo 对象，并添加到列表中
      articleVoList.add(copy(record, isTags, isAuthor, isBody, isCategory));
    }
    return articleVoList;
  }



  private ArticleVO copy(Article article, boolean isAuthor, boolean isBody, boolean isTags,
      boolean isCategory) {
    ArticleVO articleVO = new ArticleVO();
    BeanUtils.copyProperties(article, articleVO);
    articleVO.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    // 并不是 所有接口 都需要标签 作者信息

    if (isTags) {
      Long articleId = article.getId();
      articleVO.setTags(tagService.findTagsByArticleId(articleId));
    }

    if (isAuthor) {
      SysUser sysUser = sysUserService.findSysUserById(article.getAuthorId());
      articleVO.setAuthor(sysUser.getNickname());
    }

    if (isBody) {
      ArticleBodyVO articleBody = findArticleBody(article.getId());
      articleVO.setBody(articleBody);
    }
    if (isCategory) {
      CategoryVO categoryVO = findCategory(article.getCategoryId());
      articleVO.setCategory(categoryVO);
    }

    return articleVO;
  }

  private CategoryVO findCategory(Long categoryId) {
    return categoryService.findCategoryById(categoryId);

  }

  private ArticleBodyVO findArticleBody(Long articleId) {
    LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ArticleBody::getArticleId, articleId);
    ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
    ArticleBodyVO articleBodyVO = new ArticleBodyVO();
    articleBodyVO.setContent(articleBody.getContent());
    return articleBodyVO;
  }

}
