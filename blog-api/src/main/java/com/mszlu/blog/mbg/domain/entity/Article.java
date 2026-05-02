package com.mszlu.blog.mbg.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@Data
@TableName("ms_article")
@ApiModel(value = "Article对象", description = "")
public class Article implements Serializable {

  public static final int Article_TOP = 1;

  public static final int Article_Common = 0;

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 评论数量
   */
  @ApiModelProperty("评论数量")
  @TableField("comment_counts")
  private Integer commentCounts;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @TableField("create_date")
  private Long createDate;

  /**
   * 简介
   */
  @TableField("summary")
  @ApiModelProperty("简介")
  private String summary;

  /**
   * 标题
   */
  @TableField("title")
  @ApiModelProperty("标题")
  private String title;

  /**
   * 浏览数量
   */
  @ApiModelProperty("浏览数量")
  @TableField("view_counts")
  private Integer viewCounts;

  /**
   * 是否置顶
   */
  @TableField("weight")
  @ApiModelProperty("是否置顶")
  private Integer weight = Article_Common;

  /**
   * 作者id
   */
  @TableField("author_id")
  @ApiModelProperty("作者id")
  private Long authorId;

  /**
   * 内容id
   */
  @TableField("body_id")
  @ApiModelProperty("内容id")
  private Long bodyId;

  /**
   * 类别id
   */
  @ApiModelProperty("类别id")
  @TableField("category_id")
  private Long categoryId;
}
