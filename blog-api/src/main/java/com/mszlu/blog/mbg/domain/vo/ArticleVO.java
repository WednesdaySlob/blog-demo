package com.mszlu.blog.mbg.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import lombok.Data;

/**
 * 展示层对象，前端页面展示
 * @author wx
 */
@Data
public class ArticleVO {

  // 不加 会出现精度损失
  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;

  private String title;

  private String summary;

  private Integer commentCounts;

  private int viveCounts;

  private int weight;

  /**
   * 创建时间
   */
  private String createDate;

  private String author;

  private ArticleBodyVO body;

  private List<TagVO> tags;

//  private List<CategoryVO> Categories;
  private CategoryVO category;

}
