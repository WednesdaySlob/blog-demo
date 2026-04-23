package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.VO.ArticleVO;
import com.mszlu.blog.VO.TagVO;
import com.mszlu.blog.common.api.CommonPage;
import com.mszlu.blog.mbg.domain.entity.Article;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.mapper.ArticleMapper;
import com.mszlu.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 *  服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

  private final ArticleMapper articleMapper;
  private final UserService sysUserService;
  private final TagService tagService;

  @Override
  public List<ArticleVO> listArticlesPage(CommonPage commonPage) {
    QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
    Page<Article> page = new Page<>(commonPage.getPage(),commonPage.getPageSize());
    Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
    List<ArticleVO> artiocleVOList  = copyList(articlePage.getRecords(),true,false,true);
    return  artiocleVOList;
  }
  private List<ArticleVO> copyList(List<Article> records, boolean isAuthor, boolean isBody, boolean isTags) {
    List<ArticleVO> articleVOList = new ArrayList<>();
    for (Article article : records) {
      ArticleVO articleVO = copy(article,isAuthor,isBody,isTags);
      articleVOList.add(articleVO);
    }
    return articleVOList;
  }

  private ArticleVO copy(Article article, boolean isAuthor, boolean isBody, boolean isTags) {
    ArticleVO articleVO = new ArticleVO();
    BeanUtils.copyProperties(article,articleVO);
    if (isAuthor){
      SysUser sysUser = sysUserService.findSysUserById(article.getAuthorId());
      articleVO.setAuthor(sysUser.getNickname());
    }
    articleVO.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    if (isTags){
      List<TagVO> tags = tagService.findTagsByArticleId(article.getId());
      articleVO.setTags(tags);
    }
    return articleVO;
  }

}
