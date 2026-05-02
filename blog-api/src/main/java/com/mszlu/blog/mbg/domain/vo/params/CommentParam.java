package com.mszlu.blog.mbg.domain.vo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 评论 参数
 * @author wx
 */
@Data
public class CommentParam {

  // 文章id
  private Long articleId;

  // 评论内容
  private String content;

  // 父评论 id
  private Long parent;


  // 被评论的用户id
  private Long toUserId;

}
