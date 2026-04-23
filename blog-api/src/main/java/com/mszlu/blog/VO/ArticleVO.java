package com.mszlu.blog.VO;

import java.util.List;
import lombok.Data;

/**
 * 展示层对象，前端页面展示
 * @author wx
 */
@Data
public class ArticleVO {

  private Long id;

  private String title;

  private String summary;

  private int commentCounts;

  private int viveCounts;

  private int weight;

  /**
   * 创建时间
   */
  private String createDate;

  private String author;

  private ArticleBodyVO body;

  private List<TagVO> tags;

  private List<CategoryVO> Categories;

}
