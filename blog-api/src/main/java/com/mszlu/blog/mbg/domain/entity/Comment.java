package com.mszlu.blog.mbg.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
@Getter
@Setter
@TableName("ms_comment")
@ApiModel(value = "Comment对象", description = "")
public class Comment implements Serializable {

  private static final long serialVersionUID = 1L;

  //防止前端 精度损失 把id转为string
  // 分布式id 比较长，传到前端 会有精度损失，必须转为string类型 进行传输，就不会有问题了
  @TableId(value = "id", type = IdType.AUTO)
  @JsonSerialize(using = ToStringSerializer.class)
  private Long id;

  @TableField("content")
  private String content;

  @TableField("create_date")
  private Long createDate;

  @TableField("article_id")
  private Long articleId;

  @TableField("author_id")
  private Long authorId;

  @TableField("parent_id")
  private Long parentId;

  @TableField("to_uid")
  private Long toUid;

  @TableField("level")
  private Integer level;
}
