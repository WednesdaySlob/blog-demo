package com.mszlu.blog.mbg.domain.vo;

import java.util.List;
import lombok.Data;

/**
 * comment 前端展示数据
 * @author wx
 */
@Data
public class CommentVO {

  private Long id;

  private UserVO author;

  private String content;

  private List<CommentVO> children;

  private String createDate;

  private Integer level;

  private UserVO toUser;

}
