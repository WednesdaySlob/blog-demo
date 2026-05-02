package com.mszlu.blog.controller;

import com.mszlu.blog.mbg.domain.vo.TagVO;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.service.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {


  private final TagService tagService;

  @GetMapping("/hot")
  public CommonResult listHotTags() {
    int limit = 6;
    List<TagVO> tagVOList = tagService.hot(limit);
    return CommonResult.success(tagVOList);
  }

}
