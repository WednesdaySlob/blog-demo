package com.mszlu.blog.mbg.domain.vo.params;

import com.mszlu.blog.mbg.domain.vo.CategoryVO;
import com.mszlu.blog.mbg.domain.vo.TagVO;
import java.util.List;
import lombok.Data;

/**
 * 发布文章的 请求参数
 * @author wx
 */
@Data
public class ArticleParam {

  // 文章id（编辑有值）
  private Long id;

  // 文章内容
  private ArticleBodyParam body;

  // 文章类别
  private CategoryVO category;

  // 文章概述
  private String summary;

  // 文章便签
  private List<TagVO> tags;

  // 文章标题
  private String title;

}
