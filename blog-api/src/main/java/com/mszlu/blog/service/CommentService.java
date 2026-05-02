package com.mszlu.blog.service;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mszlu.blog.mbg.domain.vo.params.CommentParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
public interface CommentService extends IService<Comment> {

  CommonResult commentsByArticleId(Long articleId);

  CommonResult comment(CommentParam commentParam);

}
