package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.common.utils.UserThreadLocal;
import com.mszlu.blog.mbg.domain.entity.Comment;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.domain.vo.CommentVO;
import com.mszlu.blog.mbg.domain.vo.UserVO;
import com.mszlu.blog.mbg.domain.vo.params.CommentParam;
import com.mszlu.blog.mbg.mapper.CommentMapper;
import com.mszlu.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mszlu.blog.service.UserService;
import com.sun.source.doctree.CommentTree;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.CommentToken;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-30
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements
    CommentService {

  private final CommentMapper commentMapper;
  private final UserService userService;

  @Override
  public CommonResult commentsByArticleId(Long articleId) {
    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Comment::getArticleId, articleId).eq(Comment::getLevel, 1);
    List<Comment> comments = commentMapper.selectList(queryWrapper);
    return CommonResult.success(copyList(comments));
  }

  @Override
  public CommonResult comment(CommentParam commentParam) {
    SysUser sysUser = UserThreadLocal.get();
    Comment comment = new Comment();
    comment.setArticleId(commentParam.getArticleId());
    comment.setAuthorId(sysUser.getId());
    comment.setContent(commentParam.getContent());
    comment.setCreateDate(System.currentTimeMillis());
    Long parent = commentParam.getParent();
    if (parent == null || parent == 0) {
      comment.setLevel(1);
    } else {
      comment.setLevel(2);
    }
    comment.setParentId(parent == null ? 1 : parent);
    Long toUserId = commentParam.getToUserId();
    comment.setToUid(toUserId == null ? 1 : toUserId);
    this.commentMapper.insert(comment);
    return CommonResult.success(null);
  }

  private List<CommentVO> copyList(List<Comment> commentList) {

    ArrayList<CommentVO> commentVOList = new ArrayList<>();
    for (Comment comment : commentList) {
      commentVOList.add(copy(comment));
    }
    return commentVOList;

  }

  private CommentVO copy(Comment comment) {
    CommentVO commentVO = new CommentVO();
    BeanUtils.copyProperties(comment, commentVO);
    commentVO.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    Long authorId = comment.getAuthorId();
    UserVO userVO = userService.findUserVoById(authorId);
    commentVO.setAuthor(userVO);
    // 评论
    List<CommentVO> commentVOList = findCommentsByParentId(comment.getId());
    commentVO.setChildren(commentVOList);
    if (comment.getLevel() > 1) {
      Long toUid = comment.getToUid();
      UserVO toUserVO = userService.findUserVoById(toUid);
      commentVO.setToUser(toUserVO);
    }
    return commentVO;
  }

  private List<CommentVO> findCommentsByParentId(Long id) {
    LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Comment::getParentId, id).eq(Comment::getLevel, 2);
    List<Comment> comments = this.commentMapper.selectList(queryWrapper);
    return copyList(comments);
  }
}
