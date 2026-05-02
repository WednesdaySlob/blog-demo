package com.mszlu.blog.controller;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.vo.params.CommentParam;
import com.mszlu.blog.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @ApiOperation("评论列表")
  @GetMapping("article/{id}")
  public CommonResult comments(@PathVariable("id") Long articleId) {
    return commentService.commentsByArticleId(articleId);
  }


  @ApiOperation("修改评论")
  @PostMapping("create/change")
  public CommonResult comment(@RequestBody CommentParam commentParam) {
    return commentService.comment(commentParam);
  }

}
