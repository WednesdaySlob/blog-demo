package com.mszlu.blog.mbg.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
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
@Data
@TableName("ms_article_body")
@ApiModel(value = "ArticleBody对象", description = "")
public class ArticleBody implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("content")
    private String content;

    @TableField("content_html")
    private String contentHtml;

    @TableField("article_id")
    private Long articleId;
}
