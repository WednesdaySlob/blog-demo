package com.mszlu.blog.mbg.domain.vo;

import lombok.Data;

/**
 * 展示层对象，前端页面展示
 * @author wx
 */
@Data
public class CategoryVO {

  private Long id;

  private String avatar;

  private String categoryName;

  private String description;


}
