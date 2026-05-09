package com.mszlu.blog.mbg.domain.vo.params;

import lombok.Data;

/**
 * 文章内容 传入参数
 * @author wx
 */
@Data
public class ArticleBodyParam {

  // 原始内容（可以能是markdown）
  private String content;

  // html 格式内容
  private String contentHtml;

}
